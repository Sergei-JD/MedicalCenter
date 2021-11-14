package com.itrex.java.lab.service;

import com.itrex.java.lab.dto.TimeslotDTO;
import com.itrex.java.lab.dto.CreateTimeslotDTO;

import java.util.List;

public interface TimeslotService {

    void createTimeslot(TimeslotDTO timeslotDTO);

    boolean deleteTimeslot(int timeslotId);

    List<CreateTimeslotDTO> getAllTimeslot();

    CreateTimeslotDTO getTimeslotById(int timeslotId);

    TimeslotDTO updateTimeslot(TimeslotDTO timeslotDTO);

}
