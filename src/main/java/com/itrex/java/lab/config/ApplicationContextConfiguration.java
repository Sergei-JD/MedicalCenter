package com.itrex.java.lab.config;

import java.util.Properties;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcConnectionPool;
import org.springframework.context.annotation.*;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
@Profile({"dev", "prod", "default"})
@ComponentScan("com.itrex.java.lab")
@PropertySource("classpath:/application.properties")
public class ApplicationContextConfiguration {

    @Value("${database.driver}")
    String driverClassName;

    @Value("${hibernate.dialect.property}")
    String hibernateDialect;

    @Value("${hibernate.show_sql.property}")
    String hibernateShowSqlProperty;

    @Value("${hibernate.format_sql.property}")
    String hibernateFormatSql;

    @Value("${database.url}")
    private String url;

    @Value("${database.login}")
    private String login;

    @Value("${database.password}")
    private String password;

    @Value("${database.migration.location}")
    private String migrationLocation;

    @Value("${entity.package.to.scan}")
    private String entityPackageToScan;

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return Flyway.configure()
                .dataSource(url, login, password)
                .locations(migrationLocation)
                .load();
    }

    @Bean
    @DependsOn("flyway")
    public JdbcConnectionPool jdbcConnectionPool() {
        return JdbcConnectionPool.create(url, login, password);
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(entityPackageToScan);
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    @DependsOn("flyway")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(login);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());

        return transactionManager;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", hibernateDialect);
        hibernateProperties.setProperty("show_sql", hibernateShowSqlProperty);
        hibernateProperties.setProperty("format_sql", hibernateFormatSql);

        return hibernateProperties;
    }

}
