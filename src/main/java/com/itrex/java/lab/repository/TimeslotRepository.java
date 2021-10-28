package com.itrex.java.lab.repository;


import com.itrex.java.lab.entity.Timeslot;
import java.util.List;

public interface TimeslotRepository {

    List<Timeslot> getAllTimeslot() throws RepositoryException;

    Timeslot getTimeslotByID(int timeslotId) throws RepositoryException;

    void add(Timeslot timeslot) throws RepositoryException;

    boolean deleteTimeslotById(int timeslotId) throws RepositoryException;

}
