package com.itrex.java.lab.dto;

import com.itrex.java.lab.persistence.entity.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

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
    private Set<Role> roles;

}
