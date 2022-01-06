package com.itrex.java.lab.controller;

import com.itrex.java.lab.dto.VisitDTO;
import com.itrex.java.lab.dto.VisitViewDTO;
import com.itrex.java.lab.dto.CreateVisitDTO;
import com.itrex.java.lab.dto.VisitHistoryDTO;
import com.itrex.java.lab.service.VisitService;
import com.itrex.java.lab.exception.CustomAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import javax.annotation.security.RolesAllowed;

@RestController
@RequiredArgsConstructor
@RequestMapping("/med/visits")
public class VisitController {

    private final VisitService visitService;

    @GetMapping
    @RolesAllowed({"admin", "doctor"})
    public ResponseEntity<Page<VisitViewDTO>> getAllVisits(Pageable pageable) {
        Page<VisitViewDTO> visits = visitService.getAllVisit(pageable);
        return new ResponseEntity<>(visits, HttpStatus.OK);
    }

    @GetMapping("/free")
    @RolesAllowed({"admin", "doctor", "patient"})
    public ResponseEntity<Page<VisitViewDTO>> getAllFreeVisits(Pageable pageable) {
        Page<VisitViewDTO> visits = visitService.getAllFreeVisits(pageable);
        return new ResponseEntity<>(visits, HttpStatus.OK);
    }

    @GetMapping("/doctors/free/{id}")
    @RolesAllowed({"admin", "doctor", "patient"})
    public ResponseEntity<List<VisitViewDTO>> getAllFreeVisitsForDoctorById(@PathVariable(name = "id") int id) {
        List<VisitViewDTO> visits = visitService.getAllFreeVisitsForDoctorById(id);
        return new ResponseEntity<>(visits, HttpStatus.OK);
    }

    @GetMapping("/patients/{id}")
    @RolesAllowed({"admin", "doctor"})
    public ResponseEntity<List<VisitViewDTO>> getAllVisitsForPatientById(@PathVariable(name = "id") int id) {
        List<VisitViewDTO> visits = visitService.getAllVisitsForPatientDyId(id);
        return new ResponseEntity<>(visits, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @RolesAllowed({"admin", "doctor"})
    public ResponseEntity<VisitViewDTO> getVisitById(@PathVariable(name = "id") int id) {
        Optional<VisitViewDTO> visitViewDTO = visitService.getVisitById(id);
        return visitViewDTO.map(viewDTO -> new ResponseEntity<>(viewDTO, HttpStatus.OK))
                .orElseThrow(() -> new CustomAuthenticationException(
                        "Visit with this id: " + id + " does not exist")
                );
    }

    @PostMapping
    @RolesAllowed({"admin", "doctor"})
    public ResponseEntity<VisitDTO> createVisit(@RequestBody CreateVisitDTO createVisitDTO) {
        VisitDTO addVisit = visitService.createVisit(createVisitDTO);
        return new ResponseEntity<>(addVisit, HttpStatus.CREATED);
    }

    @PutMapping
    @RolesAllowed({"admin", "doctor"})
    public ResponseEntity<VisitDTO> updateVisit(@RequestBody VisitDTO visitDTO) {
        VisitDTO updatedVisit = visitService.updateVisit(visitDTO);
        return new ResponseEntity<>(updatedVisit, HttpStatus.OK);
    }

    @PutMapping("/history")
    @RolesAllowed({"admin", "doctor"})
    public ResponseEntity<VisitHistoryDTO> updateVisitHistory(@RequestBody VisitDTO visitDTO) {
        VisitHistoryDTO updatedVisitHistory = visitService.updateVisitHistory(visitDTO);
        return new ResponseEntity<>(updatedVisitHistory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({"admin", "doctor"})
    public ResponseEntity<Boolean> deleteVisit(@PathVariable(name = "id") int id) {
        return new ResponseEntity<>(visitService.deleteVisit(id), HttpStatus.OK);
    }

}
