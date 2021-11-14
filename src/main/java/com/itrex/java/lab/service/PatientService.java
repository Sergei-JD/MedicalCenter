package com.itrex.java.lab.service;

import com.itrex.java.lab.dto.PatientDTO;
import com.itrex.java.lab.dto.PatientViewDTO;
import com.itrex.java.lab.dto.CreatePatientDTO;

import java.util.List;

public interface PatientService {

    void createPatient(CreatePatientDTO patientDTO);

    boolean deletePatient(int patientId);

    List<PatientViewDTO> getAllPatients();

    PatientViewDTO getPatientById(int patientId);

    PatientDTO updatePatient(PatientDTO patientDTO);

}
