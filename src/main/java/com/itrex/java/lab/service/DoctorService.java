package com.itrex.java.lab.service;

import com.itrex.java.lab.dto.DoctorDTO;
import com.itrex.java.lab.dto.DoctorViewDTO;
import com.itrex.java.lab.dto.CreateDoctorDTO;

import java.util.List;
import java.util.Optional;

public interface DoctorService {

    CreateDoctorDTO createDoctor(CreateDoctorDTO doctorDTO);

    boolean deleteDoctor(int doctorId);

    List<DoctorViewDTO> getAllDoctors();

    Optional<DoctorViewDTO> getDoctorById(int doctorId);

    DoctorDTO updateDoctor(DoctorDTO doctorDTO);

}
