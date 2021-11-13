package com.itrex.java.lab.dto;


import com.itrex.java.lab.persistence.entity.Timeslot;
import com.itrex.java.lab.persistence.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VisitDTO {

    private Integer visitId;
    private User doctor;
    private Timeslot timeslot;

}
