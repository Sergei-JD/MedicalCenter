package com.itrex.java.lab.converter;

import com.itrex.java.lab.dto.TimeslotDTO;
import com.itrex.java.lab.dto.CreateTimeslotDTO;
import com.itrex.java.lab.persistence.entity.Timeslot;

public interface TimeslotConverter {

    Timeslot toTimeslot(CreateTimeslotDTO createTimeslotDTO);

    TimeslotDTO toTimeslotDTO(Timeslot timeslot);

}
