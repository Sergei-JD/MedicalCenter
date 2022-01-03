package com.itrex.java.lab.controller;

import com.itrex.java.lab.dto.DoctorDTO;
import com.itrex.java.lab.dto.DoctorViewDTO;
import com.itrex.java.lab.dto.CreateDoctorDTO;
import com.itrex.java.lab.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import javax.annotation.security.RolesAllowed;

@RestController
@RequiredArgsConstructor
@RequestMapping("/med/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    @RolesAllowed({"admin", "doctor", "patient"})
    public ResponseEntity<Page<DoctorViewDTO>> getAllDoctors(Pageable pageable) {
        Page<DoctorViewDTO> doctors = doctorService.getAllDoctors(pageable);

        return doctors != null && !doctors.isEmpty()
                ? new ResponseEntity<>(doctors, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    @RolesAllowed({"admin", "doctor", "patient"})
    public ResponseEntity<DoctorViewDTO> getDoctorById(@PathVariable(name = "id") int id) {
        Optional<DoctorViewDTO> doctorViewDTO = doctorService.getDoctorById(id);

        return doctorViewDTO.map(viewDTO -> new ResponseEntity<>(viewDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/email")
    @RolesAllowed({"admin", "doctor"})
    public ResponseEntity<DoctorDTO> getDoctorByEmail(@RequestParam(name = "email") String email) {
        Optional<DoctorDTO> doctorDTO = doctorService.getDoctorByEmail(email);

        return doctorDTO.map(viewDTO -> new ResponseEntity<>(viewDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @RolesAllowed({"admin"})
    public ResponseEntity<CreateDoctorDTO> createDoctor(@RequestBody CreateDoctorDTO createDoctorDTO) {
        CreateDoctorDTO addDoctor = doctorService.createDoctor(createDoctorDTO);

        return (addDoctor != null)
                ? new ResponseEntity<>(addDoctor, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping
    @RolesAllowed({"admin"})
    public ResponseEntity<DoctorDTO> updateDoctor(@RequestBody DoctorDTO doctorDTO) {
        DoctorDTO updatedDoctor = doctorService.updateDoctor(doctorDTO);

        return updatedDoctor != null
                ? new ResponseEntity<>(updatedDoctor, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({"admin"})
    public ResponseEntity<Boolean> deleteDoctor(@PathVariable(name = "id") int id) {
        boolean result = doctorService.deleteDoctor(id);

        return result
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
