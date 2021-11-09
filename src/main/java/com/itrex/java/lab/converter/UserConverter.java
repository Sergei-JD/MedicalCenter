package com.itrex.java.lab.converter;

import com.itrex.java.lab.dto.CreateDoctorDTO;
import com.itrex.java.lab.dto.DoctorDTO;
import com.itrex.java.lab.persistence.entity.User;

public interface UserConverter  {

    User toUser(CreateDoctorDTO createDoctorDTO);

    DoctorDTO toDoctorDto(User user);
}
