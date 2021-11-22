package com.itrex.java.lab.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.*;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@Profile({"dev", "prod", "default"})
public class ApplicationContextConfiguration {
//
//    @Value("${database.url}")
//    private String url;
//
//    @Value("${database.login}")
//    private String login;
//
//    @Value("${database.password}")
//    private String password;
//
//    @Value("${database.migration.location}")
//    private String migrationLocation;
//
//    @Bean(initMethod = "migrate")
//    public Flyway flyway() {
//        return Flyway.configure()
//                .dataSource(url, login, password)
//                .locations(migrationLocation)
//                .load();
//    }

}
