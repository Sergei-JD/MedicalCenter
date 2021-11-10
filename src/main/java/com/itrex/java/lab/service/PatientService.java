package com.itrex.java.lab.service;

import com.itrex.java.lab.dto.CreatePatientDTO;
import com.itrex.java.lab.dto.PatientDTO;

import java.util.List;

public interface PatientService {

    void createPatient(CreatePatientDTO patientDTO);

    List<PatientDTO> getAllPatients();

}
