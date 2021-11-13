package com.itrex.java.lab.converter.impl;

import com.itrex.java.lab.dto.VisitDTO;
import com.itrex.java.lab.dto.CreateVisitDTO;
import org.springframework.stereotype.Component;
import com.itrex.java.lab.persistence.entity.Visit;
import com.itrex.java.lab.converter.VisitConverter;

@Component
public class VisitConvertorImpl implements VisitConverter {

    @Override
    public Visit toVisit(CreateVisitDTO createVisitDTO) {
        return Visit.builder()
                .patient(createVisitDTO.getPatient())
                .doctor(createVisitDTO.getDoctor())
                .timeslot(createVisitDTO.getTimeslot())
                .comment(createVisitDTO.getComment())
                .build();
    }

    @Override
    public VisitDTO toVisitDTO(Visit visit) {
        return VisitDTO.builder()
                .visitId(visit.getVisitId())
                .doctor(visit.getDoctor())
                .timeslot(visit.getTimeslot())
                .build();
    }

}
