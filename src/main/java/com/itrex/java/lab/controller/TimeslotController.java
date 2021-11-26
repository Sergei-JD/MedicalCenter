package com.itrex.java.lab.controller;

import com.itrex.java.lab.dto.CreateTimeslotDTO;

import com.itrex.java.lab.dto.PatientDTO;
import com.itrex.java.lab.dto.TimeslotDTO;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.service.TimeslotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TimeslotController {

    private final TimeslotService timeslotService;

    @PostMapping("/timeslot/new")
    public ResponseEntity<CreateTimeslotDTO> createTimeslot(@RequestBody CreateTimeslotDTO createTimeslotDTO) throws ServiceException {

        CreateTimeslotDTO newTimeslot = timeslotService.createTimeslot(createTimeslotDTO);

        return (newTimeslot != null)
                ? new ResponseEntity<>(newTimeslot, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping("/timeslot/delete/{id}")
    public ResponseEntity<Boolean> deleteTimeslot(@PathVariable(name = "id") int id) throws ServiceException {

        boolean result = timeslotService.deleteTimeslot(id);

        return result
                ? new ResponseEntity<>(result, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/timeslots")
    public ResponseEntity<List<CreateTimeslotDTO>> getAllTimeslot() throws ServiceException {

        List<CreateTimeslotDTO> timeslots = timeslotService.getAllTimeslot();

        return timeslots != null && !timeslots.isEmpty()
                ? new ResponseEntity<>(timeslots, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/timeslot/{id}")
    public ResponseEntity<CreateTimeslotDTO> getTimeslotById(@PathVariable(name = "id") int id) throws ServiceException {

        Optional<CreateTimeslotDTO> timeslotDTO = timeslotService.getTimeslotById(id);

        return timeslotDTO.isPresent()
                ? new ResponseEntity<>(timeslotDTO.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/timeslot/update")
    public ResponseEntity<TimeslotDTO> updateTimeslot(@RequestBody TimeslotDTO timeslotDTO) throws ServiceException {

        TimeslotDTO updatedTimeslot = timeslotService.updateTimeslot(timeslotDTO);

        return updatedTimeslot != null
                ? new ResponseEntity<>(updatedTimeslot, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
