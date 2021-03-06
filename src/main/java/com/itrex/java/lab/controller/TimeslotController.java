package com.itrex.java.lab.controller;

import lombok.RequiredArgsConstructor;
import com.itrex.java.lab.dto.TimeslotDTO;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import com.itrex.java.lab.dto.CreateTimeslotDTO;
import org.springframework.data.domain.Pageable;
import com.itrex.java.lab.service.TimeslotService;
import com.itrex.java.lab.exception.CustomAuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import javax.annotation.security.RolesAllowed;

@RestController
@RequiredArgsConstructor
@RequestMapping("/med/timeslots")
public class TimeslotController {

    private final TimeslotService timeslotService;

    @GetMapping
    @RolesAllowed({"admin", "doctor", "patient"})
    public ResponseEntity<Page<CreateTimeslotDTO>> getAllTimeslot(Pageable pageable) {
        Page<CreateTimeslotDTO> timeslots = timeslotService.getAllTimeslot(pageable);
        return new ResponseEntity<>(timeslots, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @RolesAllowed({"admin", "doctor", "patient"})
    public ResponseEntity<CreateTimeslotDTO> getTimeslotById(@PathVariable(name = "id") int id) {
        Optional<CreateTimeslotDTO> timeslotDTO = timeslotService.getTimeslotById(id);
        return timeslotDTO.map(createTimeslotDTO -> new ResponseEntity<>(createTimeslotDTO, HttpStatus.OK))
                .orElseThrow(() -> new CustomAuthenticationException(
                        "Timeslot with this id: " + id + " does not exist")
                );
    }

    @PostMapping
    @RolesAllowed({"admin", "doctor"})
    public ResponseEntity<CreateTimeslotDTO> createTimeslot(@RequestBody CreateTimeslotDTO createTimeslotDTO) {
        CreateTimeslotDTO addTimeslot = timeslotService.createTimeslot(createTimeslotDTO);
        return new ResponseEntity<>(addTimeslot, HttpStatus.CREATED);
    }

    @PutMapping
    @RolesAllowed({"admin", "doctor"})
    public ResponseEntity<TimeslotDTO> updateTimeslot(@RequestBody TimeslotDTO timeslotDTO) {
        TimeslotDTO updatedTimeslot = timeslotService.updateTimeslot(timeslotDTO);
        return new ResponseEntity<>(updatedTimeslot, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({"admin", "doctor"})
    public ResponseEntity<Boolean> deleteTimeslot(@PathVariable(name = "id") int id) {
        return new ResponseEntity<>(timeslotService.deleteTimeslot(id), HttpStatus.OK);
    }

}
