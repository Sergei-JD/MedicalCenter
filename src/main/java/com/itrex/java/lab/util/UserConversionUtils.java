package com.itrex.java.lab.util;

import com.itrex.java.lab.dto.DoctorDTO;
import com.itrex.java.lab.dto.DoctorViewDTO;
import com.itrex.java.lab.dto.PatientDTO;
import com.itrex.java.lab.dto.PatientViewDTO;
import com.itrex.java.lab.persistence.entity.User;

import java.util.Optional;


public class UserConversionUtils {

    public static DoctorDTO toDoctorDTO(User doctor) {
        return Optional.ofNullable(doctor)
                .map(existDoctor -> DoctorDTO.builder()
                        .userId(doctor.getUserId())
                        .firstName(doctor.getFirstName())
                        .lastName(doctor.getLastName())
                        .age(doctor.getAge())
                        .email(doctor.getEmail())
                        .password(doctor.getPassword())
                        .gender(doctor.getGender())
                        .phoneNum(doctor.getPhoneNum())
                        .build())
                .orElse(null);
    }

    public static DoctorViewDTO toDoctorViewDTO(User doctor) {
        return Optional.ofNullable(doctor)
                .map(existUser -> DoctorViewDTO.builder()
                        .userId(existUser.getUserId())
                        .firstName(existUser.getFirstName())
                        .lastName(existUser.getLastName())
                        .build())
                .orElse(null);
    }

    public static PatientDTO toPatientDTO(User patient) {
        return Optional.ofNullable(patient)
                .map(existPatient -> PatientDTO.builder()
                        .userId(patient.getUserId())
                        .firstName(patient.getFirstName())
                        .lastName(patient.getLastName())
                        .age(patient.getAge())
                        .email(patient.getEmail())
                        .password(patient.getPassword())
                        .gender(patient.getGender())
                        .phoneNum(patient.getPhoneNum())
                        .build())
                .orElse(null);
    }

    public static PatientViewDTO toPatientViewDTO(User patient) {
        return Optional.ofNullable(patient)
                .map(existUser -> PatientViewDTO.builder()
                        .userId(existUser.getUserId())
                        .firstName(existUser.getFirstName())
                        .lastName(existUser.getLastName())
                        .build())
                .orElse(null);
    }

}
