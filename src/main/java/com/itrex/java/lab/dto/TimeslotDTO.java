package com.itrex.java.lab.dto;

import lombok.*;

import java.sql.Time;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeslotDTO {

    private Integer timeslotId;
    private Time startTime;
    private Date date;
    private Integer office;

}
