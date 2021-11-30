package com.itrex.java.lab.persistence.data;

import com.itrex.java.lab.persistence.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Integer> {
}
