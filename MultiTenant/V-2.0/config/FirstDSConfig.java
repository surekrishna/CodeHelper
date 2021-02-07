/*
 * package com.krish.config;
 * 
 * import java.util.Map;
 * 
 * import javax.persistence.EntityManagerFactory; import javax.sql.DataSource;
 * 
 * import org.springframework.beans.factory.annotation.Qualifier; import
 * org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.context.annotation.Primary; import
 * org.springframework.data.jpa.repository.config.EnableJpaRepositories; import
 * org.springframework.orm.jpa.JpaTransactionManager; import
 * org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean; import
 * org.springframework.transaction.PlatformTransactionManager; import
 * org.springframework.transaction.annotation.EnableTransactionManagement;
 * 
 * @Configuration
 * 
 * @EnableJpaRepositories( entityManagerFactoryRef =
 * "firstEntityManagerFactory", transactionManagerRef =
 * "firstTransactionManager", basePackages =
 * "com.vit.products.ctrlpay.repository" )
 * 
 * @EnableTransactionManagement public class FirstDSConfig {
 * 
 * @Primary
 * 
 * @Bean(name = "firstEntityManagerFactory") public
 * LocalContainerEntityManagerFactoryBean firstEntityManagerFactory(final
 * EntityManagerFactoryBuilder builder, final @Qualifier("first-db") DataSource
 * dataSource) { return builder .dataSource(dataSource)
 * .packages("com.marcosbarbero.wd.pcf.multidatasources.first.domain")
 * .persistenceUnit("krish_mat") .properties(Map.of("hibernate.hbm2ddl.auto",
 * "create-drop")) .build(); }
 * 
 * 
 * @Primary
 * 
 * @Bean(name = "firstTransactionManager") public PlatformTransactionManager
 * firstTransactionManager(@Qualifier("firstEntityManagerFactory")
 * EntityManagerFactory firstEntityManagerFactory) { return new
 * JpaTransactionManager(firstEntityManagerFactory); } }
 * 
 */
