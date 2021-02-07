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
 * "secondEntityManagerFactory", transactionManagerRef =
 * "secondTransactionManager", basePackages =
 * "com.vit.products.ctrlpay.repository.master" )
 * 
 * @EnableTransactionManagement public class SecondDSConfig {
 * 
 * @Primary
 * 
 * @Bean(name = "secondEntityManagerFactory") public
 * LocalContainerEntityManagerFactoryBean firstEntityManagerFactory(final
 * EntityManagerFactoryBuilder builder, final @Qualifier("second-db") DataSource
 * dataSource) { return builder .dataSource(dataSource)
 * .packages("com.vit.products.ctrlpay.repository.master")
 * .persistenceUnit("krish_payroll")
 * .properties(Map.of("hibernate.hbm2ddl.auto", "create-drop")) .build(); }
 * 
 * 
 * @Primary
 * 
 * @Bean(name = "secondTransactionManager") public PlatformTransactionManager
 * firstTransactionManager(@Qualifier("secondTransactionManager")
 * EntityManagerFactory firstEntityManagerFactory) { return new
 * JpaTransactionManager(firstEntityManagerFactory); } }
 */
