package com.itrex.java.lab.service;

import com.itrex.java.lab.dto.PatientDTO;
import com.itrex.java.lab.dto.PatientViewDTO;
import com.itrex.java.lab.dto.CreatePatientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PatientService {

    Page<PatientViewDTO> getAllPatients(Pageable pageable);

    Optional<PatientViewDTO> getPatientById(int patientId);

    Optional<PatientDTO> getPatientByEmail(String email);

    CreatePatientDTO createPatient(CreatePatientDTO patientDTO);

    PatientDTO updatePatient(PatientDTO patientDTO);

    boolean deletePatient(int patientId);

}
