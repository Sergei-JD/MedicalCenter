package com.itrex.java.lab.controller;

import com.itrex.java.lab.dto.*;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    @PostMapping("/visit/new")
    public ResponseEntity<CreateVisitDTO> createVisit(@RequestBody CreateVisitDTO createVisitDTO) throws ServiceException {

        CreateVisitDTO newVisit = visitService.createVisit(createVisitDTO);

        return (newVisit != null)
                ? new ResponseEntity<>(newVisit, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping("/visit/delete/{id}")
    public ResponseEntity<Boolean> deleteVisit(@PathVariable(name = "id") int id) throws ServiceException {

        boolean result = visitService.deleteVisit(id);

        return result
                ? new ResponseEntity<>(result, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/visitsAll")
    public ResponseEntity<List<VisitViewDTO>> getAllVisit() throws ServiceException {

        List<VisitViewDTO> visits = visitService.getAllVisit();

        return visits != null && !visits.isEmpty()
                ? new ResponseEntity<>(visits, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/visitsAllFree")
    public ResponseEntity<List<VisitViewDTO>> getAllFreeVisits() throws ServiceException {

        List<VisitViewDTO> visits = visitService.getAllFreeVisits();

        return visits != null && !visits.isEmpty()
                ? new ResponseEntity<>(visits, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/visitsAllFreeForDoctorById")
    public ResponseEntity<List<VisitViewDTO>> getAllFreeVisitsForDoctorById(@PathVariable(name = "id") int id) throws ServiceException {

        List<VisitViewDTO> visits = visitService.getAllFreeVisitsForDoctorById(id);

        return visits != null && !visits.isEmpty()
                ? new ResponseEntity<>(visits, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/visitsAllForPatientDyId")
    public ResponseEntity<List<VisitViewDTO>> getAllVisitsForPatientDyId(@PathVariable(name = "id") int id) throws ServiceException {

        List<VisitViewDTO> visits = visitService.getAllVisitsForPatientDyId(id);

        return visits != null && !visits.isEmpty()
                ? new ResponseEntity<>(visits, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/visit/{id}")
    public ResponseEntity<VisitViewDTO> getVisitById(@PathVariable(name = "id") int id) throws ServiceException {

        Optional<VisitViewDTO> visitViewDTO = visitService.getVisitById(id);

        return visitViewDTO.isPresent()
                ? new ResponseEntity<>(visitViewDTO.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/visit/updateVisit")
    public ResponseEntity<VisitDTO> updateVisit(@RequestBody VisitDTO visitDTO) throws ServiceException {

        VisitDTO updatedVisit = visitService.updateVisit(visitDTO);

        return updatedVisit != null
                ? new ResponseEntity<>(updatedVisit, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/visit/updateVisitHistory")
    public ResponseEntity<VisitHistoryDTO> updateVisitHistory(@RequestBody VisitDTO visitDTO) throws ServiceException {

        VisitHistoryDTO updatedVisitHistory = visitService.updateVisitHistory(visitDTO);

        return updatedVisitHistory != null
                ? new ResponseEntity<>(updatedVisitHistory, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
