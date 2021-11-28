package com.itrex.java.lab.util;

import com.itrex.java.lab.dto.DoctorDTO;
import com.itrex.java.lab.dto.PatientDTO;
import com.itrex.java.lab.dto.DoctorViewDTO;
import com.itrex.java.lab.dto.PatientViewDTO;
import com.itrex.java.lab.dto.CreateDoctorDTO;
import com.itrex.java.lab.dto.CreatePatientDTO;
import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.persistence.entity.User;

import java.util.Set;

public class UserConversionUtils {

    public static User toUser(CreateDoctorDTO createDoctorDTO) {
        return User.builder()
                .firstName(createDoctorDTO.getFirstName())
                .lastName(createDoctorDTO.getLastName())
                .age(createDoctorDTO.getAge())
                .email(createDoctorDTO.getEmail())
                .password(createDoctorDTO.getPassword())
                .gender(createDoctorDTO.getGender())
                .phoneNum(createDoctorDTO.getPhoneNum())
                .roles(Set.of(Role.builder().roleId(1).name(RoleType.DOCTOR).build()))
                .build();
        }

    public static DoctorDTO toDoctorDTO(User doctor) {
        return DoctorDTO.builder()
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .age(doctor.getAge())
                .email(doctor.getEmail())
                .password(doctor.getPassword())
                .gender(doctor.getGender())
                .phoneNum(doctor.getPhoneNum())
                .roles(Set.of(Role.builder().roleId(1).name(RoleType.DOCTOR).build()))
                .build();
    }

    public static DoctorViewDTO toDoctorViewDTO(User user) {
        return DoctorViewDTO.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roles(Set.of(Role.builder().roleId(1).name(RoleType.DOCTOR).build()))
                .build();
    }

    public static User toUser(CreatePatientDTO createPatientDTO) {
        return User.builder()
                .firstName(createPatientDTO.getFirstName())
                .lastName(createPatientDTO.getLastName())
                .age(createPatientDTO.getAge())
                .email(createPatientDTO.getEmail())
                .password(createPatientDTO.getPassword())
                .gender(createPatientDTO.getGender())
                .phoneNum(createPatientDTO.getPhoneNum())
                .roles(Set.of(Role.builder().roleId(2).name(RoleType.PATIENT).build()))
                .build();
    }

    public static PatientDTO toPatientDTO(User patient) {
        return PatientDTO.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .age(patient.getAge())
                .email(patient.getEmail())
                .password(patient.getPassword())
                .gender(patient.getGender())
                .phoneNum(patient.getPhoneNum())
                .roles(Set.of(Role.builder().roleId(2).name(RoleType.PATIENT).build()))
                .build();
    }

    public static PatientViewDTO toPatientViewDTO(User user) {
        return PatientViewDTO.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roles(Set.of(Role.builder().roleId(2).name(RoleType.PATIENT).build()))
                .build();
    }

}
