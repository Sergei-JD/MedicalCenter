package com.itrex.java.lab.converter.impl;

import com.itrex.java.lab.dto.*;
import org.springframework.stereotype.Component;
import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.converter.UserConverter;

@Component
public class UserConverterImpl implements UserConverter {

    @Override
    public User toUser(CreateDoctorDTO createDoctorDTO) {
        return User.builder()
                .firstName(createDoctorDTO.getFirstName())
                .lastName(createDoctorDTO.getLastName())
                .age(createDoctorDTO.getAge())
                .email(createDoctorDTO.getEmail())
                .password(createDoctorDTO.getPassword())
                .gender(createDoctorDTO.getGender())
                .phoneNum(createDoctorDTO.getPhoneNum())
                .build();
    }

    @Override
    public DoctorDTO toDoctorDTO(User doctor) {
        return DoctorDTO.builder()
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .age(doctor.getAge())
                .email(doctor.getEmail())
                .password(doctor.getPassword())
                .gender(doctor.getGender())
                .phoneNum(doctor.getPhoneNum())
                .build();
    }

    @Override
    public DoctorViewDTO toDoctorViewDTO(User user) {
        return DoctorViewDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    @Override
    public User toUser(CreatePatientDTO createPatientDTO) {
        return User.builder()
                .firstName(createPatientDTO.getFirstName())
                .lastName(createPatientDTO.getLastName())
                .age(createPatientDTO.getAge())
                .email(createPatientDTO.getEmail())
                .password(createPatientDTO.getPassword())
                .gender(createPatientDTO.getGender())
                .phoneNum(createPatientDTO.getPhoneNum())
                .build();
    }

    @Override
    public PatientDTO toPatientDTO(User patient) {
        return PatientDTO.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .age(patient.getAge())
                .email(patient.getEmail())
                .password(patient.getPassword())
                .gender(patient.getGender())
                .phoneNum(patient.getPhoneNum())
                .build();
    }

    @Override
    public PatientViewDTO toPatientViewDTO(User user) {
        return PatientViewDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

}
