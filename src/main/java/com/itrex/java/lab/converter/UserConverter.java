package com.itrex.java.lab.converter;

import com.itrex.java.lab.dto.*;
import com.itrex.java.lab.persistence.entity.User;

public interface UserConverter  {

    User toUser(CreateDoctorDTO createDoctorDTO);

    User toUser(CreatePatientDTO createPatientDTO);

    DoctorDTO toDoctorDTO(User doctor);

    PatientDTO toPatientDTO(User patient);

    DoctorViewDTO toDoctorViewDTO(User user);

    PatientViewDTO toPatientViewDTO(User user);

}
