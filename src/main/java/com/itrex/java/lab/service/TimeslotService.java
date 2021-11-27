package com.itrex.java.lab.service;

import com.itrex.java.lab.dto.TimeslotDTO;
import org.springframework.data.domain.Page;
import com.itrex.java.lab.dto.CreateTimeslotDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TimeslotService {

    CreateTimeslotDTO createTimeslot(CreateTimeslotDTO createTimeslotDTO);

    boolean deleteTimeslot(int timeslotId);

    List<CreateTimeslotDTO> getAllTimeslot();

    Optional<CreateTimeslotDTO> getTimeslotById(int timeslotId);

    TimeslotDTO updateTimeslot(TimeslotDTO timeslotDTO);

}
