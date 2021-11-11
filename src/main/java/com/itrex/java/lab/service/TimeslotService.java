package com.itrex.java.lab.service;

import com.itrex.java.lab.dto.TimeslotDTO;
import com.itrex.java.lab.persistence.entity.Timeslot;

import java.util.List;

public interface TimeslotService {

    Timeslot updateTimeslot(Timeslot timeslot, TimeslotDTO timeslotDTO);

    List<TimeslotDTO> getAllTimeslot();

}
