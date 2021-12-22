package com.itrex.java.lab.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

@Setter
@Getter
@Builder
public class DoctorViewDTO {

    private Integer userId;
    private String firstName;
    private String lastName;

}
