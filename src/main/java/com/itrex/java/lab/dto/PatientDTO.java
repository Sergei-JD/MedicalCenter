package com.itrex.java.lab.dto;

import com.itrex.java.lab.persistence.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PatientDTO extends CreatePatientDTO {

    private Integer userId;
    private Set<RoleType> roles;

}
