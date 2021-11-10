package com.itrex.java.lab.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PatientDTO {

    private String firstName;
    private String lastName;

}
