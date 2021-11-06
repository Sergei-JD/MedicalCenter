package com.itrex.java.lab.config;

import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.h2.jdbcx.JdbcConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan("com.itrex.java.lab")
@PropertySource("classpath:/application.properties")
public class ApplicationContextConfiguration {

    @Autowired
    private Environment environment;

    @Bean
    @DependsOn("flyway")
    public JdbcConnectionPool jdbcConnectionPool() {
        return JdbcConnectionPool.create(
                environment.getProperty("database.url"),
                environment.getProperty("database.login"),
                environment.getProperty("database.password")
        );
    }

    @Bean
    @DependsOn("flyway")
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return Flyway.configure()
                .dataSource(environment.getProperty("database.url"),
                        environment.getProperty("database.login"),
                        environment.getProperty("database.password"))
                .locations(environment.getProperty("database.migration.location"))
                .load();

    }

}
