package com.itrex.java.lab.repository;


import com.itrex.java.lab.entity.Timeslot;
import com.itrex.java.lab.repository.impl.RepositoryException;
import java.util.List;

public interface TimeslotRepository {

    List<Timeslot> getAllTimeslot() throws RepositoryException;

    Timeslot getTimeslotByID(int timeslotId) throws RepositoryException;

    void addTimeslot(Timeslot timeslot) throws RepositoryException;

    void deleteTimeslot(int timeslotId) throws RepositoryException;

}
