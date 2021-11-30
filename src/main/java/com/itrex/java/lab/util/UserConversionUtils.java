package com.itrex.java.lab.util;

import com.itrex.java.lab.dto.CreateDoctorDTO;
import com.itrex.java.lab.dto.CreatePatientDTO;
import com.itrex.java.lab.dto.DoctorDTO;
import com.itrex.java.lab.dto.DoctorViewDTO;
import com.itrex.java.lab.dto.PatientDTO;
import com.itrex.java.lab.dto.PatientViewDTO;
import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.persistence.entity.User;

import java.util.Optional;
import java.util.Set;

public class UserConversionUtils {

    public static DoctorDTO toDoctorDTO(User doctor) {
        return DoctorDTO.builder()
                .userId(doctor.getUserId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .age(doctor.getAge())
                .email(doctor.getEmail())
                .password(doctor.getPassword())
                .gender(doctor.getGender())
                .phoneNum(doctor.getPhoneNum())
                .build();
    }

    public static DoctorViewDTO toDoctorViewDTO(User user) {
        return Optional.ofNullable(user)
                .map(existUser -> DoctorViewDTO.builder()
                        .userId(existUser.getUserId())
                        .firstName(existUser.getFirstName())
                        .lastName(existUser.getLastName())
                        .build())
                .orElse(null);
    }

    public static PatientDTO toPatientDTO(User patient) {
        return PatientDTO.builder()
                .userId(patient.getUserId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .age(patient.getAge())
                .email(patient.getEmail())
                .password(patient.getPassword())
                .gender(patient.getGender())
                .phoneNum(patient.getPhoneNum())
                .build();
    }

    public static PatientViewDTO toPatientViewDTO(User user) {
        return PatientViewDTO.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

}
