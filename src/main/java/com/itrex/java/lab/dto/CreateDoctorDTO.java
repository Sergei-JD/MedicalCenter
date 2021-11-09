package com.itrex.java.lab.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateDoctorDTO {

    private Integer userId;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String password;
    private String gender;
    private Integer phoneNum;

}
