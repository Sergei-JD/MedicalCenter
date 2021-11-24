package com.itrex.java.lab.persistence;

import org.springframework.stereotype.Repository;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Rollback
@ActiveProfiles("test")
@DataJpaTest(includeFilters =
@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
public abstract class BaseRepositoryTest {
}
