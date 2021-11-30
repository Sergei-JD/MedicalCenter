package com.itrex.java.lab.service.hibernate;


import com.itrex.java.lab.dto.CreateTimeslotDTO;
import com.itrex.java.lab.dto.TimeslotDTO;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.persistence.data.TimeslotRepository;
import com.itrex.java.lab.persistence.entity.Timeslot;
import com.itrex.java.lab.service.TimeslotService;
import com.itrex.java.lab.util.TimeslotConversionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
    public TimeslotDTO createTimeslot(CreateTimeslotDTO timeslotDTO) {
        try {
            Timeslot newTimeslot = Timeslot.builder()
                    .startTime(timeslotDTO.getStartTime())
                    .date(timeslotDTO.getDate())
                    .office(timeslotDTO.getOffice())
                    .build();

            return TimeslotConversionUtils.toTimeslotDTO(timeslotRepository.save(newTimeslot));
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to create timeslot.\n" + ex);
        }
    }

    @Override
    public List<CreateTimeslotDTO> getAllTimeslot() {
        try {
            List<Timeslot> timeslots = timeslotRepository.findAll();

            return timeslots.stream()
                    .map(TimeslotConversionUtils::toTimeslotDTO)
                    .collect(Collectors.toList());
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to get all timeslots.\n" + ex);
        }
    }

    @Override
    public Optional<CreateTimeslotDTO> getTimeslotById(int timeslotId) {
        CreateTimeslotDTO timeslotDTO = null;

        Optional<Timeslot> timeslot = timeslotRepository.findById(timeslotId);
        if (timeslot.isPresent()) {
            timeslotDTO = TimeslotConversionUtils.toTimeslotDTO(timeslot.get());
        }

        return Optional.ofNullable(timeslotDTO);
    }

    @Override
    @Transactional
    public boolean deleteTimeslot(int timeslotId) {
        timeslotRepository.deleteById(timeslotId);

        return timeslotRepository.findById(timeslotId).isEmpty();
    }

    @Override
    @Transactional
    public TimeslotDTO updateTimeslot(TimeslotDTO timeslotDTO) {
        if (!isValidTimeslotDTO(timeslotDTO) || timeslotDTO.getTimeslotId() == null) {
            throw new ServiceException("Failed to update timeslot. Not valid timeslotDTO.");
        }
        Timeslot timeslot = timeslotRepository.findById(timeslotDTO.getTimeslotId())
                .orElseThrow(() -> new ServiceException("Failed to update timeslot no such timeslot"));

        timeslot.setOffice(timeslotDTO.getOffice());
        timeslot.setStartTime(timeslotDTO.getStartTime());
        timeslot.setDate(timeslotDTO.getDate());

        timeslotRepository.save(timeslot);

        return TimeslotConversionUtils.toTimeslotDTO(timeslot);
    }

    private boolean isValidTimeslotDTO(CreateTimeslotDTO timeslotDTO) {
        return timeslotDTO != null && timeslotDTO.getDate() != null && timeslotDTO.getStartTime() != null
                && timeslotDTO.getOffice() != null;
    }

}
