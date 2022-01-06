package com.itrex.java.lab.dto;

import lombok.Data;
import lombok.NonNull;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePatientDTO {

    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private Integer age;
    private String email;
    private String password;
    @NonNull
    private String gender;
    private Integer phoneNum;

}
