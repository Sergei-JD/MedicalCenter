package com.itrex.java.lab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CreateVisitDTO {

    private Integer doctorId;
    private Integer patientId;
    private Integer timeslotId;
    private String comment;

}
