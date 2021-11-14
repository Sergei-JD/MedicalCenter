package com.itrex.java.lab.service;

import com.itrex.java.lab.dto.DoctorDTO;
import com.itrex.java.lab.dto.DoctorViewDTO;
import com.itrex.java.lab.dto.CreateDoctorDTO;

import java.util.List;

public interface DoctorService {

    void createDoctor(CreateDoctorDTO doctorDTO);

    boolean deleteDoctor(int doctorId);

    List<DoctorViewDTO> getAllDoctors();

    DoctorViewDTO getDoctorById(int doctorId);

    DoctorDTO updateDoctor(DoctorDTO doctorDTO);

}
