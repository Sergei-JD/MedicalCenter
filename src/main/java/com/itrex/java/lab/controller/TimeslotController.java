package com.itrex.java.lab.controller;

import com.itrex.java.lab.dto.CreateTimeslotDTO;
import com.itrex.java.lab.dto.TimeslotDTO;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.service.TimeslotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/med/timeslots")
public class TimeslotController {

    private final TimeslotService timeslotService;

    @PostMapping("/add")
    public ResponseEntity<CreateTimeslotDTO> createTimeslot(@RequestBody CreateTimeslotDTO createTimeslotDTO) throws ServiceException {

        CreateTimeslotDTO addTimeslot = timeslotService.createTimeslot(createTimeslotDTO);

        return (addTimeslot != null)
                ? new ResponseEntity<>(addTimeslot, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteTimeslot(@PathVariable(name = "id") int id) throws ServiceException {

        boolean result = timeslotService.deleteTimeslot(id);

        return result
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping
    public ResponseEntity<List<CreateTimeslotDTO>> getAllTimeslot() throws ServiceException {

        List<CreateTimeslotDTO> timeslots = timeslotService.getAllTimeslot();

        return timeslots != null && !timeslots.isEmpty()
                ? new ResponseEntity<>(timeslots, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateTimeslotDTO> getTimeslotById(@PathVariable(name = "id") int id) throws ServiceException {

        Optional<CreateTimeslotDTO> timeslotDTO = timeslotService.getTimeslotById(id);

        return timeslotDTO.map(createTimeslotDTO -> new ResponseEntity<>(createTimeslotDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    public ResponseEntity<TimeslotDTO> updateTimeslot(@RequestBody TimeslotDTO timeslotDTO) throws ServiceException {

        TimeslotDTO updatedTimeslot = timeslotService.updateTimeslot(timeslotDTO);

        return updatedTimeslot != null
                ? new ResponseEntity<>(updatedTimeslot, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
