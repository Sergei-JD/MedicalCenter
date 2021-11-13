package com.itrex.java.lab.dto;

import com.itrex.java.lab.persistence.entity.Timeslot;
import com.itrex.java.lab.persistence.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class CreateVisitDTO {

    private Integer visitId;
    private User patient;
    private User doctor;
    private Timeslot timeslot;
    private String comment;

}
