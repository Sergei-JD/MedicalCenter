package com.itrex.java.lab.controller;

import com.itrex.java.lab.dto.CreatePatientDTO;
import com.itrex.java.lab.dto.DoctorDTO;
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
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/med/patients")
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/add")
    @RolesAllowed({"ADMIN", "DOCTOR"})
    public ResponseEntity<CreatePatientDTO> createPatient(@RequestBody CreatePatientDTO createPatientDTO) throws ServiceException {
        CreatePatientDTO addPatient = patientService.createPatient(createPatientDTO);

        return (addPatient != null)
                ? new ResponseEntity<>(addPatient, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({"ADMIN", "DOCTOR"})
    public ResponseEntity<Boolean> deletePatient(@PathVariable(name = "id") int id) throws ServiceException {
        boolean result = patientService.deletePatient(id);

        return result
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping
    @RolesAllowed({"ADMIN", "DOCTOR"})
    public ResponseEntity<Page<PatientViewDTO>> getAllPatients(Pageable pageable) throws ServiceException {
        Page<PatientViewDTO> patients = patientService.getAllPatients(pageable);

        return patients != null && !patients.isEmpty()
                ? new ResponseEntity<>(patients, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    @RolesAllowed({"ADMIN", "DOCTOR"})
    public ResponseEntity<PatientViewDTO> getPatientById(@PathVariable(name = "id") int id) throws ServiceException {
        Optional<PatientViewDTO> patientViewDTO = patientService.getPatientById(id);

        return patientViewDTO.map(viewDTO -> new ResponseEntity<>(viewDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/email")
    @RolesAllowed({"ADMIN", "DOCTOR"})
    public ResponseEntity<PatientDTO> getPatientByEmail(@RequestParam(name = "email") String email) throws ServiceException {
        Optional<PatientDTO> patientDTO = patientService.getPatientByEmail(email);

        return patientDTO.map(viewDTO -> new ResponseEntity<>(viewDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    @RolesAllowed({"ADMIN", "DOCTOR"})
    public ResponseEntity<PatientDTO> updatePatient(@RequestBody PatientDTO patientDTO) throws ServiceException {
        PatientDTO updatedPatient = patientService.updatePatient(patientDTO);

        return updatedPatient != null
                ? new ResponseEntity<>(updatedPatient, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
