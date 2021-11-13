package com.itrex.java.lab.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@Builder
public class CreateTimeslotDTO {

    private Integer timeslotId;
    private Time startTime;
    private Date date;
    private Integer office;

}
