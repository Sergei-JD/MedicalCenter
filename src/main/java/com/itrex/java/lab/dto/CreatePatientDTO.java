package com.itrex.java.lab.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
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
