package com.itrex.java.lab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePatientDTO {

    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String password;
    private String gender;
    private Integer phoneNum;

}
