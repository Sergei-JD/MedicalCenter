package com.itrex.java.lab.dto;

import com.itrex.java.lab.persistence.entity.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Builder
public class DoctorViewDTO {

    private Integer userId;
    private String firstName;
    private String lastName;

}
