package com.itrex.java.lab.util;

import com.itrex.java.lab.dto.DoctorViewDTO;
import com.itrex.java.lab.dto.PatientViewDTO;
import com.itrex.java.lab.dto.TimeslotDTO;
import com.itrex.java.lab.dto.VisitDTO;
import com.itrex.java.lab.dto.VisitHistoryDTO;
import com.itrex.java.lab.dto.VisitViewDTO;
import com.itrex.java.lab.persistence.entity.Visit;

import java.util.Optional;

public class VisitConversionUtils {

    public static VisitViewDTO toVisitViewDTO(Visit visit) {
        DoctorViewDTO doctor = Optional.ofNullable(visit.getDoctor()).map(UserConversionUtils::toDoctorViewDTO).orElse(null);
        PatientViewDTO patient = Optional.ofNullable(visit.getPatient()).map(UserConversionUtils::toPatientViewDTO).orElse(null);
        TimeslotDTO timeslot = Optional.ofNullable(visit.getTimeslot()).map(TimeslotConversionUtils::toTimeslotDTO).orElse(null);

        return VisitViewDTO.builder()
                .visitId(visit.getVisitId())
                .patient(patient)
                .doctor(doctor)
                .timeslot(timeslot)
                .build();
    }

    public static VisitDTO toVisitDTO(Visit visit) {
        return Optional.ofNullable(visit)
                .map(existVisit -> VisitDTO.builder()
                        .visitId(visit.getVisitId())
                        .patientId(visit.getPatient().getUserId())
                        .doctorId(visit.getDoctor().getUserId())
                        .timeslotId(visit.getTimeslot().getTimeslotId())
                        .comment(visit.getComment())
                        .build())
                .orElse(null);
    }

    public static VisitHistoryDTO toVisitHistoryDTO(Visit visit) {
        return Optional.ofNullable(visit)
                .map(existVisit -> VisitHistoryDTO.builder()
                        .visitId(visit.getVisitId())
                        .comment(visit.getComment())
                        .build())
                .orElse(null);
    }

}
