package com.itrex.java.lab.service;

import com.itrex.java.lab.dto.DoctorDTO;
import com.itrex.java.lab.dto.DoctorViewDTO;
import com.itrex.java.lab.dto.CreateDoctorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DoctorService {

    Page<DoctorViewDTO> getAllDoctors(Pageable pageable);

    Optional<DoctorViewDTO> getDoctorById(int doctorId);

    Optional<DoctorDTO> getDoctorByEmail(String email);

    CreateDoctorDTO createDoctor(CreateDoctorDTO doctorDTO);

    DoctorDTO updateDoctor(DoctorDTO doctorDTO);

    boolean deleteDoctor(int doctorId);

}
