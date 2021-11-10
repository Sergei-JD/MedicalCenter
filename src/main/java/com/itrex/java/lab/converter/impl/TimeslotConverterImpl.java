package com.itrex.java.lab.converter.impl;

import com.itrex.java.lab.converter.TimeslotConverter;
import com.itrex.java.lab.dto.CreateTimeslotDTO;
import com.itrex.java.lab.persistence.entity.Timeslot;
import org.springframework.stereotype.Component;

@Component
public class TimeslotConverterImpl implements TimeslotConverter {

    @Override
    public Timeslot toTimeslot(CreateTimeslotDTO createTimeslotDTO) {
        return Timeslot.builder()
                .startTime(createTimeslotDTO.getStartTime())
                .date(createTimeslotDTO.getDate())
                .build();
    }

}
