package com.itrex.java.lab.controller;

import com.itrex.java.lab.dto.VisitDTO;
import com.itrex.java.lab.dto.VisitViewDTO;
import com.itrex.java.lab.dto.CreateVisitDTO;
import com.itrex.java.lab.dto.VisitHistoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import com.itrex.java.lab.service.VisitService;
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

        return visits != null && !visits.isEmpty()
                ? new ResponseEntity<>(visits, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/free")
    @RolesAllowed({"admin", "doctor", "patient"})
    public ResponseEntity<Page<VisitViewDTO>> getAllFreeVisits(Pageable pageable) {
        Page<VisitViewDTO> visits = visitService.getAllFreeVisits(pageable);

        return visits != null && !visits.isEmpty()
                ? new ResponseEntity<>(visits, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/doctors/free/{id}")
    @RolesAllowed({"admin", "doctor", "patient"})
    public ResponseEntity<List<VisitViewDTO>> getAllFreeVisitsForDoctorById(@PathVariable(name = "id") int id) {
        List<VisitViewDTO> visits = visitService.getAllFreeVisitsForDoctorById(id);

        return visits != null && !visits.isEmpty()
                ? new ResponseEntity<>(visits, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/patients/{id}")
    @RolesAllowed({"admin", "doctor"})
    public ResponseEntity<List<VisitViewDTO>> getAllVisitsForPatientById(@PathVariable(name = "id") int id) {
        List<VisitViewDTO> visits = visitService.getAllVisitsForPatientDyId(id);

        return visits != null && !visits.isEmpty()
                ? new ResponseEntity<>(visits, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    @RolesAllowed({"admin", "doctor"})
    public ResponseEntity<VisitViewDTO> getVisitById(@PathVariable(name = "id") int id) {
        Optional<VisitViewDTO> visitViewDTO = visitService.getVisitById(id);

        return visitViewDTO.map(viewDTO -> new ResponseEntity<>(viewDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @RolesAllowed({"admin", "doctor"})
    public ResponseEntity<VisitDTO> createVisit(@RequestBody CreateVisitDTO createVisitDTO) {
        VisitDTO addVisit = visitService.createVisit(createVisitDTO);

        return (addVisit != null)
                ? new ResponseEntity<>(addVisit, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping
    @RolesAllowed({"admin", "doctor"})
    public ResponseEntity<VisitDTO> updateVisit(@RequestBody VisitDTO visitDTO) {
        VisitDTO updatedVisit = visitService.updateVisit(visitDTO);

        return updatedVisit != null
                ? new ResponseEntity<>(updatedVisit, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/history")
    @RolesAllowed({"admin", "doctor"})
    public ResponseEntity<VisitHistoryDTO> updateVisitHistory(@RequestBody VisitDTO visitDTO) {
        VisitHistoryDTO updatedVisitHistory = visitService.updateVisitHistory(visitDTO);

        return updatedVisitHistory != null
                ? new ResponseEntity<>(updatedVisitHistory, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({"admin", "doctor"})
    public ResponseEntity<Boolean> deleteVisit(@PathVariable(name = "id") int id) {
        boolean result = visitService.deleteVisit(id);

        return result
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
