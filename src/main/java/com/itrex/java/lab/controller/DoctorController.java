package com.itrex.java.lab.controller;

import com.itrex.java.lab.dto.CreateDoctorDTO;
import com.itrex.java.lab.dto.DoctorDTO;
import com.itrex.java.lab.dto.DoctorViewDTO;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.service.DoctorService;
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
@RequestMapping("/med/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("/add")
    @RolesAllowed({"admin"})
    public ResponseEntity<CreateDoctorDTO> createDoctor(@RequestBody CreateDoctorDTO createDoctorDTO) throws ServiceException {
        CreateDoctorDTO addDoctor = doctorService.createDoctor(createDoctorDTO);

        return (addDoctor != null)
                ? new ResponseEntity<>(addDoctor, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({"admin"})
    public ResponseEntity<Boolean> deleteDoctor(@PathVariable(name = "id") int id) throws ServiceException {
        boolean result = doctorService.deleteDoctor(id);

        return result
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping
    @RolesAllowed({"admin", "doctor"})
    public ResponseEntity<Page<DoctorViewDTO>> getAllDoctors(Pageable pageable) throws ServiceException {
        Page<DoctorViewDTO> doctors = doctorService.getAllDoctors(pageable);

        return doctors != null && !doctors.isEmpty()
                ? new ResponseEntity<>(doctors, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    @RolesAllowed({"admin", "doctor"})
    public ResponseEntity<DoctorViewDTO> getDoctorById(@PathVariable(name = "id") int id) throws ServiceException {
        Optional<DoctorViewDTO> doctorViewDTO = doctorService.getDoctorById(id);

        return doctorViewDTO.map(viewDTO -> new ResponseEntity<>(viewDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/email")
    @RolesAllowed({"admin", "doctor"})
    public ResponseEntity<DoctorDTO> getDoctorByEmail(@RequestParam(name = "email") String email) throws ServiceException {
        Optional<DoctorDTO> doctorDTO = doctorService.getDoctorByEmail(email);

        return doctorDTO.map(viewDTO -> new ResponseEntity<>(viewDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    @RolesAllowed({"admin"})
    public ResponseEntity<DoctorDTO> updateDoctor(@RequestBody DoctorDTO doctorDTO) throws ServiceException {
        DoctorDTO updatedDoctor = doctorService.updateDoctor(doctorDTO);

        return updatedDoctor != null
                ? new ResponseEntity<>(updatedDoctor, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
