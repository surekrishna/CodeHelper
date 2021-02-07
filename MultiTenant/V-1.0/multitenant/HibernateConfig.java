package com.krish.config.multitenant;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class HibernateConfig {
	
	@Autowired
    private JpaProperties jpaProperties;
    
    @Autowired
    public TenantDataSource tenantDataSource;
    
    @Autowired
    private Environment environment;
    
    @Primary
    @Bean
    public DataSource getDataSource() {
    	return tenantDataSource.getDataSource(environment.getProperty("krish.datasource.schema"));
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource,
            MultiTenantConnectionProvider multiTenantConnectionProviderImpl,
            CurrentTenantIdentifierResolver currentTenantIdentifierResolverImpl) {

        Map<String, Object> jpaPropertiesMap = new HashMap<>(jpaProperties.getProperties());
        jpaPropertiesMap.put("hibernate.multiTenancy", MultiTenancyStrategy.SCHEMA);
        jpaPropertiesMap.put("hibernate.multi_tenant_connection_provider", multiTenantConnectionProviderImpl);
        jpaPropertiesMap.put("hibernate.tenant_identifier_resolver", currentTenantIdentifierResolverImpl);
        jpaPropertiesMap.put("hibernate.show_sql", environment.getProperty("krish.datasource.show-sql"));
        jpaPropertiesMap.put("hibernate.format_sql", environment.getProperty("krish.datasource.format_sql"));

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.krish.entity");
        em.setJpaVendorAdapter(this.jpaVendorAdapter());
        em.setJpaPropertyMap(jpaPropertiesMap);
        return em;
    }
}
