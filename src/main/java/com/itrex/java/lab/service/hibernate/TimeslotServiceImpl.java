package com.itrex.java.lab.service.hibernate;


import lombok.RequiredArgsConstructor;
import com.itrex.java.lab.dto.TimeslotDTO;
import org.springframework.stereotype.Service;
import com.itrex.java.lab.dto.CreateTimeslotDTO;
import com.itrex.java.lab.service.TimeslotService;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.persistence.entity.Timeslot;
import com.itrex.java.lab.util.TimeslotConversionUtils;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.persistence.repository.TimeslotRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeslotServiceImpl implements TimeslotService {

    private final TimeslotRepository timeslotRepository;

    @Override
    @Transactional
    public CreateTimeslotDTO createTimeslot(CreateTimeslotDTO createTimeslotDTO) {
        try {
            Timeslot timeslot = TimeslotConversionUtils.toTimeslot(createTimeslotDTO);

            timeslotRepository.add(timeslot);
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to create timeslot.\n" + ex);
        }

        return createTimeslotDTO;
    }

    @Override
    public List<CreateTimeslotDTO> getAllTimeslot() {
        try {
            List<Timeslot> timeslots = timeslotRepository.getAllTimeslots();

            return timeslots.stream()
                    .map(TimeslotConversionUtils::toTimeslotDTO)
                    .collect(Collectors.toList());
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to get all timeslots.\n" + ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CreateTimeslotDTO> getTimeslotById(int timeslotId) {
        CreateTimeslotDTO timeslotDTO = null;
        try {
            Optional<Timeslot> timeslot = timeslotRepository.getTimeslotById(timeslotId);
            if (timeslot.isPresent()) {
                timeslotDTO = TimeslotConversionUtils.toTimeslotDTO(timeslot.get());
            }
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to get timeslot by id " + timeslotId + ".\n" + ex);
        }

        return Optional.ofNullable(timeslotDTO);
    }

    @Override
    @Transactional
    public boolean deleteTimeslot(int timeslotId) {
        try {
            return timeslotRepository.deleteTimeslotById(timeslotId);
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to delete timeslot by id" + timeslotId + ".\n" + ex);
        }
    }

    @Override
    @Transactional
    public TimeslotDTO updateTimeslot(TimeslotDTO timeslotDTO) {
        if (!isValidTimeslotDTO(timeslotDTO) || timeslotDTO.getTimeslotId() == null) {
            throw new ServiceException("Failed to update timeslot. Not valid timeslotDTO.");
        }
        Timeslot timeslot = timeslotRepository.getTimeslotById(timeslotDTO.getTimeslotId())
                .orElseThrow(() -> new ServiceException("Failed to update timeslot no such timeslot"));

        timeslot.setOffice(timeslotDTO.getOffice());
        timeslot.setStartTime(timeslotDTO.getStartTime());
        timeslot.setDate(timeslotDTO.getDate());

        try {
            timeslotRepository.update(timeslot);
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to update timeslot.\n" + ex);
        }

        return TimeslotConversionUtils.toTimeslotDTO(timeslot);
    }

    private boolean isValidTimeslotDTO(CreateTimeslotDTO timeslotDTO) {
        return timeslotDTO != null && timeslotDTO.getDate() != null && timeslotDTO.getStartTime() != null
                && timeslotDTO.getOffice() != null;
    }

}
