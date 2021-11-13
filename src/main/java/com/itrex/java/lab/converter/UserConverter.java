package com.itrex.java.lab.converter;

import com.itrex.java.lab.dto.DoctorDTO;
import com.itrex.java.lab.dto.PatientDTO;
import com.itrex.java.lab.dto.CreateDoctorDTO;
import com.itrex.java.lab.dto.CreatePatientDTO;
import com.itrex.java.lab.persistence.entity.User;

public interface UserConverter  {

    User toUser(CreateDoctorDTO createDoctorDTO);

    User toUser(CreatePatientDTO createPatientDTO);

    DoctorDTO toDoctorDto(User user);

    PatientDTO toPatientDto(User user);

}
