package com.itrex.java.lab.persistence.data;

import com.itrex.java.lab.persistence.entity.Visit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends CrudRepository<Visit, Integer> {
}
