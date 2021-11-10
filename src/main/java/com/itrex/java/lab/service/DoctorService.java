package com.itrex.java.lab.service;

import com.itrex.java.lab.dto.CreateDoctorDTO;
import com.itrex.java.lab.dto.DoctorDTO;

import java.util.List;

public interface DoctorService {

    void createDoctor(CreateDoctorDTO doctorDTO);

    void  deleteDoctor(DoctorDTO doctorDTO);

    List<DoctorDTO> getAllDoctors();

}
