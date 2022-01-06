package com.itrex.java.lab.service;

import com.itrex.java.lab.dto.TimeslotDTO;
import com.itrex.java.lab.dto.CreateTimeslotDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TimeslotService {

    Page<CreateTimeslotDTO> getAllTimeslot(Pageable pageable);

    Optional<CreateTimeslotDTO> getTimeslotById(int timeslotId);

    CreateTimeslotDTO createTimeslot(CreateTimeslotDTO createTimeslotDTO);

    TimeslotDTO updateTimeslot(TimeslotDTO timeslotDTO);

    boolean deleteTimeslot(int timeslotId);

}
