package com.itrex.java.lab.persistence.repository;

import com.itrex.java.lab.persistence.entity.Visit;

import java.util.List;
import java.util.Optional;

public interface VisitRepository {

    List<Visit> getAllVisits();

    Optional<Visit> getVisitById(Integer visitId);

    Visit add(Visit visit);

    void update(Visit visit);

    boolean deleteVisitById(Integer visitId);

}
