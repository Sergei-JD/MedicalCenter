package com.itrex.java.lab.service;

import com.itrex.java.lab.dto.DoctorDTO;
import com.itrex.java.lab.dto.CreateDoctorDTO;

import java.util.List;

public interface DoctorService {

    void createDoctor(CreateDoctorDTO doctorDTO);

    void deleteDoctor(DoctorDTO doctorDTO);

//    void scheduleDoctor(DoctorDTO doctorDTO, TimeslotDTO timeslotDTO);

    List<DoctorDTO> getAllDoctors();

}
