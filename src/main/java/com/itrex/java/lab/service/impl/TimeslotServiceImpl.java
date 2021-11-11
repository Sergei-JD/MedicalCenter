package com.itrex.java.lab.service.impl;

import com.itrex.java.lab.converter.TimeslotConverter;
import com.itrex.java.lab.dto.TimeslotDTO;
import com.itrex.java.lab.persistence.entity.Timeslot;
import com.itrex.java.lab.persistence.repository.TimeslotRepository;
import com.itrex.java.lab.service.TimeslotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeslotServiceImpl implements TimeslotService {

    private final TimeslotRepository timeslotRepository;
    private final TimeslotConverter timeslotConverter;

    @Override
    public Timeslot updateTimeslot(Timeslot timeslot, TimeslotDTO timeslotDTO) {
        if (timeslot != null && timeslotDTO != null) {
            timeslot.setStartTime(timeslotDTO.getStartTime());
            timeslot.setDate(timeslotDTO.getDate());
        }
        return timeslot;
    }

    @Override
    public List<TimeslotDTO> getAllTimeslot() {
        List<Timeslot> timeslots = timeslotRepository.getAllTimeslots();
        return timeslots.stream()
                .map(timeslotConverter::toTimeslotDTO)
                .collect(Collectors.toList());
    }
}
