package com.itrex.java.lab.converter;

import com.itrex.java.lab.dto.TimeslotDTO;
import com.itrex.java.lab.persistence.entity.Timeslot;

public interface TimeslotConverter {

    TimeslotDTO toTimeslotDTO(Timeslot timeslot);
}
