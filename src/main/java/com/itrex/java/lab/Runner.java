package com.itrex.java.lab;

import com.itrex.java.lab.service.FlywayService;

public class Runner {

    private static final String MIGRATIONS_LOCATION = "db/migration";
    private static final String H2_URL = "jdbc:h2:mem:PUBLIC;DB_CLOSE_DELAY=-1";
    private static final String H2_USER = "sa";
    private static final String H2_PASSWORD = "";

    public static void main(String[] args) {
        System.out.println("===================START APP======================");
        System.out.println("================START MIGRATION===================");
        FlywayService flywayService = new FlywayService(MIGRATIONS_LOCATION, H2_URL, H2_USER, H2_PASSWORD);
        flywayService.migrate();

        while (true) {

        }
    }

}
