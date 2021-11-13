package com.itrex.java.lab.service;

import com.itrex.java.lab.dto.DoctorDTO;
import com.itrex.java.lab.dto.CreateDoctorDTO;

import java.util.List;

public interface DoctorService {

    void createDoctor(CreateDoctorDTO doctorDTO);

    boolean deleteDoctor(int doctorId);

//    void scheduleDoctor(DoctorDTO doctorDTO, TimeslotDTO timeslotDTO);

    List<DoctorDTO> getAllDoctors();

    DoctorDTO getDoctorById(int doctorId);

}
