package com.itrex.java.lab.persistence;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.itrex.java.lab.config.TestRepositoryConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestRepositoryConfiguration.class)
public abstract class BaseRepositoryTest {

    @Autowired
    private Flyway flyway;

    @BeforeEach
    public void migrate() {
        flyway.migrate();
    }

    @AfterEach
    public void clean() {
        flyway.clean();
    }
}
