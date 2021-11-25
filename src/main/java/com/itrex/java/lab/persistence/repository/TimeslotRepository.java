package com.itrex.java.lab.persistence.repository;

import com.itrex.java.lab.persistence.entity.Timeslot;

import java.util.List;
import java.util.Optional;

public interface TimeslotRepository {

    List<Timeslot> getAllTimeslots();

    Optional<Timeslot> getTimeslotById(Integer timeslotId);

    Timeslot add(Timeslot timeslot);

    Timeslot update(Timeslot timeslot);

    boolean deleteTimeslotById(Integer timeslotId);

}
