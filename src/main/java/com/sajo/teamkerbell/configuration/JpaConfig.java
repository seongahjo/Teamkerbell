package com.sajo.teamkerbell.configuration;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by seongahjo on 2016. 7. 14..
 */
@Configuration

@EnableJpaRepositories(basePackages = "com.sajo.teamkerbell.repository")
@EnableTransactionManagement
@PropertySource("classpath:spring.properties")
@ComponentScan({"com.sajo.teamkerbell.service", "com.sajo.teamkerbell.serviceImpl"})
public class JpaConfig {

    @Bean
    public DataSource dataSource(Environment env) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("app.jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("app.jdbc.url"));
        dataSource.setUsername(env.getProperty("app.jdbc.username"));
        dataSource.setPassword(env.getProperty("app.jdbc.password"));
        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(Environment env) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPersistenceProvider(new HibernatePersistenceProvider());
        factory.setPackagesToScan("com.sajo.teamkerbell.entity");
        factory.setDataSource(dataSource(env));
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.show_sql", true);
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
//        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        jpaProperties.put("hibernate.connection.characterEncoding", "utf8");
        jpaProperties.put("hibernate.connection.CharSet", "utf8");
        factory.setJpaProperties(jpaProperties);
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(Environment env) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory(env));
        return txManager;
    }
}
