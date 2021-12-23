package com.itrex.java.lab.service.dataimpl;

import com.itrex.java.lab.dto.TimeslotDTO;
import com.itrex.java.lab.dto.CreateTimeslotDTO;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.persistence.entity.Timeslot;
import com.itrex.java.lab.persistence.dataimpl.TimeslotRepository;
import com.itrex.java.lab.service.TimeslotService;
import com.itrex.java.lab.util.TimeslotConversionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<CreateTimeslotDTO> getAllTimeslot(Pageable pageable) {
        Page<Timeslot> pageTimeslots = timeslotRepository.findAll(pageable);

        List<CreateTimeslotDTO> timeslots = pageTimeslots.stream()
                .map(TimeslotConversionUtils::toTimeslotDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(timeslots);
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
    public TimeslotDTO createTimeslot(CreateTimeslotDTO timeslotDTO) {
        Timeslot newTimeslot = Timeslot.builder()
                .startTime(timeslotDTO.getStartTime())
                .date(timeslotDTO.getDate())
                .office(timeslotDTO.getOffice())
                .build();

        return TimeslotConversionUtils.toTimeslotDTO(timeslotRepository.save(newTimeslot));
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

    @Override
    @Transactional
    public boolean deleteTimeslot(int timeslotId) {
        timeslotRepository.deleteById(timeslotId);

        return timeslotRepository.findById(timeslotId).isEmpty();
    }

    private boolean isValidTimeslotDTO(CreateTimeslotDTO timeslotDTO) {
        return timeslotDTO != null &&
                timeslotDTO.getDate() != null &&
                timeslotDTO.getStartTime() != null &&
                timeslotDTO.getOffice() != null;
    }

}
