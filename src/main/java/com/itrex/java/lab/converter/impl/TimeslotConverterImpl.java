package com.itrex.java.lab.converter.impl;

import com.itrex.java.lab.converter.TimeslotConverter;
import com.itrex.java.lab.dto.TimeslotDTO;
import com.itrex.java.lab.persistence.entity.Timeslot;
import org.springframework.stereotype.Component;

@Component
public class TimeslotConverterImpl implements TimeslotConverter {

    @Override
    public Timeslot toTimeslot(TimeslotDTO timeslotDTO) {
        return Timeslot.builder()
                .startTime(timeslotDTO.getStartTime())
                .date(timeslotDTO.getDate())
                .build();
    }

}
