package com.itrex.java.lab.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class PatientDTO extends CreatePatientDTO {

    private Integer userId;

}
