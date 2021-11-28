package com.itrex.java.lab.controller;

import com.itrex.java.lab.dto.*;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/med/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("/add")
    public ResponseEntity<CreateDoctorDTO> createDoctor(@RequestBody CreateDoctorDTO createDoctorDTO) throws ServiceException {

        CreateDoctorDTO addDoctor = doctorService.createDoctor(createDoctorDTO);

        return (addDoctor != null)
                ? new ResponseEntity<>(addDoctor, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteDoctor(@PathVariable(name = "id") int id) throws ServiceException {

        boolean result = doctorService.deleteDoctor(id);

        return result
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping
    public ResponseEntity<List<DoctorViewDTO>> getAllDoctors() throws ServiceException {

        List<DoctorViewDTO> doctors = doctorService.getAllDoctors();

        return doctors != null && !doctors.isEmpty()
                ? new ResponseEntity<>(doctors, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorViewDTO> getDoctorById(@PathVariable(name = "id") int id) throws ServiceException {

        Optional<DoctorViewDTO> doctorViewDTO = doctorService.getDoctorById(id);

        return doctorViewDTO.map(viewDTO -> new ResponseEntity<>(viewDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDTO> updateDoctor(@RequestBody DoctorDTO doctorDTO, @PathVariable int id) throws ServiceException {

        DoctorDTO updatedDoctor = doctorService.updateDoctor(doctorDTO);

        return updatedDoctor != null
                ? new ResponseEntity<>(updatedDoctor, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
