package com.itrex.java.lab.service;

import org.flywaydb.core.Flyway;

import static com.itrex.java.lab.properties.Properties.*;

public class FlywayService {

    private Flyway flyway;

    public FlywayService(String migrationLocation) {
        inti(migrationLocation);
    }

    public void migrate() {
        flyway.migrate();
    }

    public void clean() {
        flyway.clean();
    }

    private void inti(String migrationLocation) {
        flyway = Flyway.configure()
                .dataSource(H2_URL, H2_USER, H2_PASSWORD)
                .locations(migrationLocation)
                .load();
    }

}