package com.itrex.java.lab.controller;

import com.itrex.java.lab.dto.*;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/new")
    public ResponseEntity<CreatePatientDTO> createPatient(@RequestBody CreatePatientDTO createPatientDTO) throws ServiceException {

        CreatePatientDTO newPatient = patientService.createPatient(createPatientDTO);

        return (newPatient != null)
                ? new ResponseEntity<>(newPatient, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePatient(@PathVariable(name = "id") int id) throws ServiceException {

        boolean result = patientService.deletePatient(id);

        return result
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping
    public ResponseEntity<List<PatientViewDTO>> getAllPatients() throws ServiceException {

        List<PatientViewDTO> patients = patientService.getAllPatients();

        return patients != null && !patients.isEmpty()
                ? new ResponseEntity<>(patients, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientViewDTO> getPatientById(@PathVariable(name = "id") int id) throws ServiceException {

        Optional<PatientViewDTO> patientViewDTO = patientService.getPatientById(id);

        return patientViewDTO.map(viewDTO -> new ResponseEntity<>(viewDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@RequestBody PatientDTO patientDTO, @PathVariable int id) throws ServiceException {

        PatientDTO updatedPatient = patientService.updatePatient(patientDTO);

        return updatedPatient != null
                ? new ResponseEntity<>(updatedPatient, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
