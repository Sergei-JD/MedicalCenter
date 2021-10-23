package com.itrex.java.lab.repository;

import com.itrex.java.lab.entity.Visit;
import com.itrex.java.lab.repository.impl.RepositoryException;

import java.util.List;

public interface VisitRepository {

    List<Visit> getAllVisit() throws RepositoryException;

    Visit getVisitById(int timeslotId) throws RepositoryException;

    void addVisit(Visit visit) throws RepositoryException;

    void deleteVisit(int visitId) throws RepositoryException;

}
