package com.itrex.java.lab.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@Builder
public class VisitViewDTO {

    private Integer visitId;
    private DoctorViewDTO doctor;
    private PatientViewDTO patient;
    private TimeslotDTO timeslot;

}
