package com.itrex.java.lab.service;

import com.itrex.java.lab.dto.TimeslotDTO;
import com.itrex.java.lab.dto.CreateTimeslotDTO;

import java.util.List;
import java.util.Optional;

public interface TimeslotService {

    void createTimeslot(TimeslotDTO timeslotDTO);

    boolean deleteTimeslot(int timeslotId);

    List<CreateTimeslotDTO> getAllTimeslot();

    Optional<CreateTimeslotDTO> getTimeslotById(int timeslotId);

    TimeslotDTO updateTimeslot(TimeslotDTO timeslotDTO);

}
