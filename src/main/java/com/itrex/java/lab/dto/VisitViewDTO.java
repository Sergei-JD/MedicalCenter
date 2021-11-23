package com.itrex.java.lab.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.persistence.entity.Timeslot;

@Getter
@Setter
@Builder
public class VisitViewDTO {

    private Integer visitId;
    private User doctor;
    private User patient;
    private Timeslot timeslot;

}
