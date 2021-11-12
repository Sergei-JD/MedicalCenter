package com.itrex.java.lab.persistence;

import com.itrex.java.lab.persistence.config.TestRepositoryConfiguration;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
