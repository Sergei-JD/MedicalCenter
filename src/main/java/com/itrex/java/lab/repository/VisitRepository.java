package com.itrex.java.lab.repository;

import com.itrex.java.lab.entity.Visit;
import com.itrex.java.lab.exception.RepositoryException;

import java.util.List;
import java.util.Optional;

public interface VisitRepository {

    List<Visit> getAllVisits() throws RepositoryException;

    Optional<Visit> getVisitById(int visitId) throws RepositoryException;

    Visit add(Visit visit) throws RepositoryException;

    boolean deleteVisitById(int visitId) throws RepositoryException;

}
