package com.itrex.java.lab.persistence.data;

import org.springframework.stereotype.Repository;
import com.itrex.java.lab.persistence.entity.Visit;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface VisitRepository extends CrudRepository<Visit, Integer> {
}
