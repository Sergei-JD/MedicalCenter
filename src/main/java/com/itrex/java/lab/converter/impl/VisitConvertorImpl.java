package com.itrex.java.lab.converter.impl;

import com.itrex.java.lab.dto.VisitDTO;
import com.itrex.java.lab.dto.VisitViewDTO;
import com.itrex.java.lab.dto.CreateVisitDTO;
import com.itrex.java.lab.dto.VisitHistoryDTO;
import org.springframework.stereotype.Component;
import com.itrex.java.lab.persistence.entity.Visit;
import com.itrex.java.lab.converter.VisitConverter;

@Component
public class VisitConvertorImpl implements VisitConverter {

    @Override
    public Visit toVisit(CreateVisitDTO visitDTO) {
        return Visit.builder()
                .patient(visitDTO.getPatientId())
                .doctor(visitDTO.getDoctorId())
                .timeslot(visitDTO.getTimeslotId())
                .comment(visitDTO.getComment())
                .build();
    }

    @Override
    public VisitViewDTO toVisitViewDTO(Visit visit) {
        return VisitViewDTO.builder()
                .visitId(visit.getVisitId())
                .doctor(visit.getDoctor())
                .timeslot(visit.getTimeslot())
                .build();
    }

    @Override
    public VisitDTO toVisitDTO(Visit visit) {
        return VisitDTO.builder()
                .visitId(visit.getVisitId())
                .patientId(visit.getPatient())
                .doctorId(visit.getDoctor())
                .timeslotId(visit.getTimeslot())
                .comment(visit.getComment())
                .build();
    }

    @Override
    public VisitHistoryDTO toVisitHistoryDTO(Visit visit) {
        return VisitHistoryDTO.builder()
                .comment(visit.getComment())
                .build();
    }

}
