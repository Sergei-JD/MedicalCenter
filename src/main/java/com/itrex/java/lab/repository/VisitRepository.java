package com.itrex.java.lab.repository;

import com.itrex.java.lab.entity.Visit;

import java.util.List;

public interface VisitRepository {

    List<Visit> getAllVisit() throws RepositoryException;

    Visit getVisitById(int timeslotId) throws RepositoryException;

    void add(Visit visit) throws RepositoryException;

    boolean deleteVisitById(int visitId) throws RepositoryException;

}
