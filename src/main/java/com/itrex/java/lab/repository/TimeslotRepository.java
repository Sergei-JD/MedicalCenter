package com.itrex.java.lab.repository;

import com.itrex.java.lab.entity.Timeslot;
import com.itrex.java.lab.exception_handler.RepositoryException;

import java.util.List;
import java.util.Optional;

public interface TimeslotRepository {

    List<Timeslot> getAllTimeslots() throws RepositoryException;

    Optional<Timeslot> getTimeslotById(int timeslotId) throws RepositoryException;

    Timeslot add(Timeslot timeslot) throws RepositoryException;

    boolean deleteTimeslotById(int timeslotId) throws RepositoryException;

}
