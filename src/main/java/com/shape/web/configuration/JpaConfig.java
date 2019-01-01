package com.shape.web.configuration;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
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

@EnableJpaRepositories(basePackages = "com.shape.web.repository")
@EnableTransactionManagement
@PropertySource("classpath:spring.properties")
@ComponentScan({"com.shape.web.service", "com.shape.web.serviceImpl"})
public class JpaConfig {
    @Autowired
    private Environment env;


    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("app.jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("app.jdbc.url"));
        dataSource.setUsername(env.getProperty("app.jdbc.username"));
        dataSource.setPassword(env.getProperty("app.jdbc.password"));
        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPersistenceProvider(new HibernatePersistenceProvider());
        factory.setPackagesToScan("com.shape.web.entity");
        factory.setDataSource(dataSource());
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.show_sql", true);
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        jpaProperties.put("hibernate.connection.characterEncoding", "utf8");
        jpaProperties.put("hibernate.connection.CharSet", "utf8");
        factory.setJpaProperties(jpaProperties);
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }
}
