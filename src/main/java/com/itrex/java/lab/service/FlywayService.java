package com.itrex.java.lab.service;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.api.configuration.ClassicConfiguration;

public class FlywayService {

    private final String migrationsLocation;
    private final String url;
    private final String user;
    private final String password;

    public FlywayService(String migrationsLocation, String url, String user, String password) {
        this.migrationsLocation = migrationsLocation;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public void migrate() {
        Location location = new Location(migrationsLocation);
        ClassicConfiguration configuration = new ClassicConfiguration();
        configuration.setUrl(url);
        configuration.setUser(user);
        configuration.setPassword(password);
        configuration.setLocations(location);
        configuration.setDataSource(url, user, password);
        Flyway flyway = new Flyway(configuration);
        flyway.migrate();
    }

}