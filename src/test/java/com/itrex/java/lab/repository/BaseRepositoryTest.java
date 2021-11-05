package com.itrex.java.lab.repository;

import org.flywaydb.core.Flyway;
import org.h2.engine.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;


public abstract class BaseRepositoryTest {

    private static final String MIGRATIONS_LOCATION = "db/test/migration";

    @Autowired
    private Flyway flyway;
    @Autowired
    private Session session;



//    @BeforeEach
//    public void initDB() {
//        flywayService.migrate();
//    }

    @AfterEach
    public void clean() {
        flyway.clean();
    }

}
