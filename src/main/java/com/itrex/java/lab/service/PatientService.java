package com.itrex.java.lab.service;

import com.itrex.java.lab.dto.PatientDTO;
import com.itrex.java.lab.dto.PatientViewDTO;
import com.itrex.java.lab.dto.CreatePatientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PatientService {

    CreatePatientDTO createPatient(CreatePatientDTO patientDTO);

    boolean deletePatient(int patientId);

    Page<PatientViewDTO> getAllPatients(Pageable pageable);

    Optional<PatientViewDTO> getPatientById(int patientId);

    PatientDTO updatePatient(PatientDTO patientDTO);

}
