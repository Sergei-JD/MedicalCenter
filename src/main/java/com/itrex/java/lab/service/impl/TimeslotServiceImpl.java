package com.itrex.java.lab.service.impl;

import lombok.RequiredArgsConstructor;
import com.itrex.java.lab.dto.TimeslotDTO;
import org.springframework.stereotype.Service;
import com.itrex.java.lab.dto.CreateTimeslotDTO;
import com.itrex.java.lab.service.TimeslotService;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.persistence.entity.Timeslot;
import com.itrex.java.lab.converter.TimeslotConverter;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.persistence.repository.TimeslotRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeslotServiceImpl implements TimeslotService {

    private final TimeslotRepository timeslotRepository;
    private final TimeslotConverter timeslotConverter;

    @Override
    public void createTimeslot(CreateTimeslotDTO timeslotDTO) {
        try {
            Timeslot timeslot = timeslotConverter.toTimeslot(timeslotDTO);

            timeslotRepository.add(timeslot);
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to create timeslot.\n" + ex);
        }
    }

    @Override
    public Timeslot updateTimeslot(Timeslot timeslot, CreateTimeslotDTO timeslotDTO) {
        try {
            if (timeslot != null && timeslotDTO != null) {
                timeslot.setStartTime(timeslotDTO.getStartTime());
                timeslot.setDate(timeslotDTO.getDate());
                timeslot.setOffice(timeslotDTO.getOffice());
            }

            return timeslot;
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to update timeslot.\n" + ex);
        }
    }

    @Override
    public List<TimeslotDTO> getAllTimeslot() {
        try {
            List<Timeslot> timeslots = timeslotRepository.getAllTimeslots();

            return timeslots.stream()
                    .map(timeslotConverter::toTimeslotDTO)
                    .collect(Collectors.toList());
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to get all timeslots.\n" + ex);
        }
    }

    @Override
    public TimeslotDTO getTimeslotById(int timeslotId) {
        TimeslotDTO timeslotDTO = null;
        try {
            Optional<Timeslot> timeslot = timeslotRepository.getTimeslotById(timeslotId);
            if (timeslot.isPresent()) {
                timeslotDTO = timeslotConverter.toTimeslotDTO(timeslot.get());
            }
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to get timeslot by id " + timeslotId + ".\n" + ex);
        }

        return timeslotDTO;
    }

    @Override
    public boolean deleteTimeslot(int timeslotId) {
        try {
            return timeslotRepository.deleteTimeslotById(timeslotId);
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to delete timeslot by id" + timeslotId + ".\n" + ex);
        }
    }

}
