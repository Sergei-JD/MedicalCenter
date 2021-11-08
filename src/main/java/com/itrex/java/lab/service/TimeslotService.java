package com.itrex.java.lab.service;

import com.itrex.java.lab.dto.TimeslotDTO;
import com.itrex.java.lab.entity.Timeslot;
import com.itrex.java.lab.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface TimeslotService {

    List<TimeslotDTO> getAllTimeslots() throws ServiceException;

    Optional<TimeslotDTO> getTimeslotById(int timeslotId) throws ServiceException;

    TimeslotDTO add(Timeslot timeslot) throws ServiceException;

    boolean deleteTimeslotById(int timeslotId) throws ServiceException;

}
