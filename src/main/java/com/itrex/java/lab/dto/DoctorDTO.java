package com.itrex.java.lab.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.itrex.java.lab.persistence.entity.RoleType;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DoctorDTO extends CreateDoctorDTO {

    private Integer userId;
    private Set<RoleType> roles;


}
