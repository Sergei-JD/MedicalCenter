package com.itrex.java.lab.service.impl;

import com.itrex.java.lab.converter.TimeslotConverter;
import com.itrex.java.lab.dto.CreateTimeslotDTO;
import com.itrex.java.lab.persistence.entity.Timeslot;
import com.itrex.java.lab.persistence.repository.TimeslotRepository;
import com.itrex.java.lab.service.TimeslotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimeslotServiceImpl implements TimeslotService {

    private final TimeslotRepository timeslotRepository;
    private final TimeslotConverter timeslotConverter;

    @Override
    public void createTimeslot(CreateTimeslotDTO timeslotDTO) {
//        Timeslot timeslot = timeslotConverter.toTimeslot(timeslotDTO);
//        timeslot.
    }
}
