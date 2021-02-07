package com.krish.config.multitenant;

import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = {"com.krish.entity", "com.krish.entity.master"})
@EnableJpaRepositories(basePackages = {"com.krish.repository", "com.krish.repository.master"})
public class HibernateConfig {
	
    @Autowired
    private JpaProperties jpaProperties;
    
    @Autowired
	private Environment environment;
    
	@Bean(name = "dataSources" )
	public Map<String, ComboPooledDataSource> dataSources() throws PropertyVetoException {
		Map<String, ComboPooledDataSource> result = new HashMap<>();
		ComboPooledDataSource ds = new ComboPooledDataSource();
		ds.setDriverClass(environment.getProperty("krish.datasource.driver-class-name"));
		ds.setJdbcUrl(environment.getProperty("krish.datasource.url") + "/" + environment.getProperty("krish.datasource.schema"));
		ds.setUser(environment.getProperty("krish.datasource.username"));
		ds.setPassword(environment.getProperty("krish.datasource.password"));
		ds.setInitialPoolSize(10);
		ds.setMinPoolSize(1);
		ds.setAcquireIncrement(1);
		ds.setMaxPoolSize(30);
		result.put(environment.getProperty("krish.datasource.schema"), ds);
		return result;
	}

	@Primary
    @Bean
    public DataSource getDataSource() throws PropertyVetoException {
    	return dataSources().entrySet().iterator().next().getValue();
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
        jpaPropertiesMap.put("hibernate.dialect", environment.getProperty("krish.jpa.hibernate.dialect"));
        jpaPropertiesMap.put("hibernate.show_sql", environment.getProperty("krish.datasource.show_sql"));
        jpaPropertiesMap.put("hibernate.format_sql", environment.getProperty("krish.datasource.format_sql"));

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.krish.entity", "com.krish.entity.master");
        em.setJpaVendorAdapter(this.jpaVendorAdapter());
        em.setJpaPropertyMap(jpaPropertiesMap);
        return em;
    }
}
