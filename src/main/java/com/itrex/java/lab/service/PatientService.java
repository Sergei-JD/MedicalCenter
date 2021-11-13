package com.itrex.java.lab.service;

import com.itrex.java.lab.dto.PatientDTO;
import com.itrex.java.lab.dto.CreatePatientDTO;

import java.util.List;

public interface PatientService {

    void createPatient(CreatePatientDTO patientDTO);

    boolean deletePatient(int patientId);

    List<PatientDTO> getAllPatients();

    PatientDTO getPatientById(int patientId);

    void updateHistory(int patientId);

}
