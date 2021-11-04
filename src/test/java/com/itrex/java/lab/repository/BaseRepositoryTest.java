package com.itrex.java.lab.repository;

import com.itrex.java.lab.config.ApplicationContextConfiguration;
import com.itrex.java.lab.repository.config_test.ApplicationContextConfigurationTest;
import com.itrex.java.lab.service.FlywayService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public abstract class BaseRepositoryTest {

    private static final String MIGRATIONS_LOCATION = "db/test/migration";
    private final FlywayService flywayService;
    private final ApplicationContext applicationContext;

    public BaseRepositoryTest () {
        applicationContext = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);
        flywayService = applicationContext.getBean(FlywayService.class);
    }

    @BeforeEach
    public void initDB() {
        flywayService.migrate();
    }

    @AfterEach
    public void cleanDB() {
        flywayService.clean();
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
