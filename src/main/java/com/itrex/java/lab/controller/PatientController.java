package com.itrex.java.lab.controller;

import com.itrex.java.lab.dto.CreatePatientDTO;
import com.itrex.java.lab.dto.PatientDTO;
import com.itrex.java.lab.dto.PatientViewDTO;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/med/patients")
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    @RolesAllowed({"admin", "doctor"})
    public ResponseEntity<Page<PatientViewDTO>> getAllPatients(Pageable pageable) {
        Page<PatientViewDTO> patients = patientService.getAllPatients(pageable);

        return patients != null && !patients.isEmpty()
                ? new ResponseEntity<>(patients, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    @RolesAllowed({"admin", "doctor"})
    public ResponseEntity<PatientViewDTO> getPatientById(@PathVariable(name = "id") int id) {
        Optional<PatientViewDTO> patientViewDTO = patientService.getPatientById(id);

        return patientViewDTO.map(viewDTO -> new ResponseEntity<>(viewDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/email")
    @RolesAllowed({"admin", "doctor"})
    public ResponseEntity<PatientDTO> getPatientByEmail(@RequestParam(name = "email") String email) {
        Optional<PatientDTO> patientDTO = patientService.getPatientByEmail(email);

        return patientDTO.map(viewDTO -> new ResponseEntity<>(viewDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @RolesAllowed({"admin", "doctor"})
    public ResponseEntity<CreatePatientDTO> createPatient(@RequestBody CreatePatientDTO createPatientDTO) {
        CreatePatientDTO addPatient = patientService.createPatient(createPatientDTO);

        return (addPatient != null)
                ? new ResponseEntity<>(addPatient, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping
    @RolesAllowed({"admin", "doctor"})
    public ResponseEntity<PatientDTO> updatePatient(@RequestBody PatientDTO patientDTO) {
        PatientDTO updatedPatient = patientService.updatePatient(patientDTO);

        return updatedPatient != null
                ? new ResponseEntity<>(updatedPatient, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({"admin", "doctor"})
    public ResponseEntity<Boolean> deletePatient(@PathVariable(name = "id") int id) {
        boolean result = patientService.deletePatient(id);

        return result
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
