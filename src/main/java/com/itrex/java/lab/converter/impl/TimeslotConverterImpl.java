package com.itrex.java.lab.converter.impl;

import com.itrex.java.lab.dto.TimeslotDTO;
import com.itrex.java.lab.dto.CreateTimeslotDTO;
import org.springframework.stereotype.Component;
import com.itrex.java.lab.converter.TimeslotConverter;
import com.itrex.java.lab.persistence.entity.Timeslot;

@Component
public class TimeslotConverterImpl implements TimeslotConverter {

    @Override
    public Timeslot toTimeslot(CreateTimeslotDTO timeslotDTO) {
        return Timeslot.builder()
                .startTime(timeslotDTO.getStartTime())
                .date(timeslotDTO.getDate())
                .office(timeslotDTO.getOffice())
                .build();
    }

    @Override
    public TimeslotDTO toTimeslotDTO(Timeslot timeslot) {
        return TimeslotDTO.builder()
                .timeslotId(timeslot.getTimeslotId())
                .startTime(timeslot.getStartTime())
                .date(timeslot.getDate())
                .office(timeslot.getOffice())
                .build();
    }

}
