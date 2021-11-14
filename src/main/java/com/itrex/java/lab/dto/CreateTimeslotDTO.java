package com.itrex.java.lab.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTimeslotDTO {

    private Time startTime;
    private Date date;
    private Integer office;

}
