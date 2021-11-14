package com.itrex.java.lab.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class DoctorDTO extends CreateDoctorDTO {

    private Integer userId;

}
