package com.itrex.java.lab.converter.impl;

import com.itrex.java.lab.converter.UserConverter;
import com.itrex.java.lab.dto.CreateDoctorDTO;
import com.itrex.java.lab.dto.CreatePatientDTO;
import com.itrex.java.lab.dto.DoctorDTO;
import com.itrex.java.lab.dto.PatientDTO;
import com.itrex.java.lab.persistence.entity.User;
import org.springframework.stereotype.Component;

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
    public DoctorDTO toDoctorDto(User user) {
        return DoctorDTO.builder()
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
    public PatientDTO toPatientDto(User user) {
        return PatientDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

}
