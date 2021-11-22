package com.itrex.java.lab.util;

import com.itrex.java.lab.dto.CreateVisitDTO;
import com.itrex.java.lab.dto.VisitDTO;
import com.itrex.java.lab.dto.VisitHistoryDTO;
import com.itrex.java.lab.dto.VisitViewDTO;
import com.itrex.java.lab.persistence.entity.Visit;

public class VisitConversionUtils {

    public static Visit toVisit(CreateVisitDTO visitDTO) {
        return Visit.builder()
                .patient(visitDTO.getPatientId())
                .doctor(visitDTO.getDoctorId())
                .timeslot(visitDTO.getTimeslotId())
                .comment(visitDTO.getComment())
                .build();
    }

    public static VisitViewDTO toVisitViewDTO(Visit visit) {
        return VisitViewDTO.builder()
                .visitId(visit.getVisitId())
                .doctor(visit.getDoctor())
                .timeslot(visit.getTimeslot())
                .build();
    }

    public static VisitDTO toVisitDTO(Visit visit) {
        return VisitDTO.builder()
                .visitId(visit.getVisitId())
                .patientId(visit.getPatient())
                .doctorId(visit.getDoctor())
                .timeslotId(visit.getTimeslot())
                .comment(visit.getComment())
                .build();
    }

    public static VisitHistoryDTO toVisitHistoryDTO(Visit visit) {
        return VisitHistoryDTO.builder()
                .comment(visit.getComment())
                .build();
    }
}
