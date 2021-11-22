//package com.itrex.java.lab.persistence.config;
//
//import org.flywaydb.core.Flyway;
//import org.springframework.context.annotation.Bean;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class TestRepositoryConfiguration {
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
//
//}
