package com.itrex.java.lab.service;

import com.itrex.java.lab.dto.TimeslotDTO;
import com.itrex.java.lab.dto.CreateTimeslotDTO;
import com.itrex.java.lab.persistence.entity.Timeslot;

import java.util.List;

public interface TimeslotService {

    void createTimeslot(CreateTimeslotDTO timeslotDTO);

    boolean deleteTimeslot(int timeslotId);

    List<TimeslotDTO> getAllTimeslot();

    TimeslotDTO getTimeslotById(int timeslotId);

    Timeslot updateTimeslot(Timeslot timeslot, CreateTimeslotDTO timeslotDTO);

}
