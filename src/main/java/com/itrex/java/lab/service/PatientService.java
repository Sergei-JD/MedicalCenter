package com.itrex.java.lab.service;

import com.itrex.java.lab.dto.PatientDTO;
import com.itrex.java.lab.dto.PatientViewDTO;
import com.itrex.java.lab.dto.CreatePatientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PatientService {

    CreatePatientDTO createPatient(CreatePatientDTO patientDTO);

    boolean deletePatient(int patientId);

    Page<PatientViewDTO> getAllPatients(Pageable pageable);

    Optional<PatientViewDTO> getPatientById(int patientId);

    Optional<PatientDTO> getPatientByEmail(String email);

    PatientDTO updatePatient(PatientDTO patientDTO);

}
