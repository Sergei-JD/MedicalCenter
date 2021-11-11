package com.itrex.java.lab.converter.impl;

import com.itrex.java.lab.converter.TimeslotConverter;
import com.itrex.java.lab.dto.TimeslotDTO;
import com.itrex.java.lab.persistence.entity.Timeslot;
import org.springframework.stereotype.Component;

@Component
public class TimeslotConverterImpl implements TimeslotConverter {

    @Override
    public TimeslotDTO toTimeslotDTO(Timeslot timeslot) {
        return TimeslotDTO.builder()
                .startTime(timeslot.getStartTime())
                .date(timeslot.getDate())
                .build();
    }

}
