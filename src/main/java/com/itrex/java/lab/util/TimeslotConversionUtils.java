package com.itrex.java.lab.util;

import com.itrex.java.lab.dto.CreateTimeslotDTO;
import com.itrex.java.lab.dto.TimeslotDTO;
import com.itrex.java.lab.persistence.entity.Timeslot;

public class TimeslotConversionUtils {

    public static Timeslot toTimeslot(CreateTimeslotDTO timeslotDTO) {
        return Timeslot.builder()
                .startTime(timeslotDTO.getStartTime())
                .date(timeslotDTO.getDate())
                .office(timeslotDTO.getOffice())
                .build();
    }

    public static TimeslotDTO toTimeslotDTO(Timeslot timeslot) {
        return TimeslotDTO.builder()
                .timeslotId(timeslot.getTimeslotId())
                .startTime(timeslot.getStartTime())
                .date(timeslot.getDate())
                .office(timeslot.getOffice())
                .build();
    }
}