package com.itrex.java.lab.converter.impl;

import com.itrex.java.lab.dto.TimeslotDTO;
import org.springframework.stereotype.Component;
import com.itrex.java.lab.dto.CreateTimeslotDTO;
import com.itrex.java.lab.converter.TimeslotConverter;
import com.itrex.java.lab.persistence.entity.Timeslot;

@Component
public class TimeslotConverterImpl implements TimeslotConverter {

    @Override
    public Timeslot toTimeslot(CreateTimeslotDTO createTimeslotDTO) {
        return Timeslot.builder()
                .startTime(createTimeslotDTO.getStartTime())
                .date(createTimeslotDTO.getDate())
                .office(createTimeslotDTO.getOffice())
                .build();
    }

    @Override
    public TimeslotDTO toTimeslotDTO(Timeslot timeslot) {
        return TimeslotDTO.builder()
                .startTime(timeslot.getStartTime())
                .date(timeslot.getDate())
                .build();
    }

}
