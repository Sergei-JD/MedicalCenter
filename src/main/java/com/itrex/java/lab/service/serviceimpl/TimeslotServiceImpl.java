package com.itrex.java.lab.service.serviceimpl;

import org.modelmapper.ModelMapper;
import com.itrex.java.lab.dto.TimeslotDTO;
import com.itrex.java.lab.entity.Timeslot;
import com.itrex.java.lab.service.TimeslotService;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.repository.TimeslotRepository;
import com.itrex.java.lab.exception.RepositoryException;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class TimeslotServiceImpl implements TimeslotService {

    private final TimeslotRepository timeslotRepository;
    private final ModelMapper modelMapper;

    public TimeslotServiceImpl(@Qualifier("hibernateTimeslotRepositoryImpl") TimeslotRepository timeslotRepository, ModelMapper modelMapper) {
        this.timeslotRepository = timeslotRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TimeslotDTO> getAllTimeslots() throws ServiceException {
        List<TimeslotDTO> allTimeslotDTOList = new ArrayList<>();
        try {
            List<Timeslot> timeslots = timeslotRepository.getAllTimeslots();

            if (timeslots.size() != 0) {
                allTimeslotDTOList = timeslots.stream()
                        .map(this::convertTimeslotIntoTimeslotDTO)
                        .collect(Collectors.toList());
            }
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage());
        }

        return allTimeslotDTOList;
    }

    @Override
    public Optional<TimeslotDTO> getTimeslotById(int timeslotId) throws ServiceException {
        TimeslotDTO timeslotDTO = null;
        try {
            Optional<Timeslot> timeslot = timeslotRepository.getTimeslotById(timeslotId);

            if (timeslot.isPresent()) {
                timeslotDTO = convertTimeslotIntoTimeslotDTO(timeslot.get());
            }
        } catch (RepositoryException ex) {
            throw  new ServiceException(ex.getMessage());
        }

        return Optional.ofNullable(timeslotDTO);
    }

    @Override
    public TimeslotDTO add(Timeslot timeslot) throws ServiceException {
        TimeslotDTO newTimeslotDTO = null;
        try {
            Timeslot newTimeslot = timeslotRepository.add(timeslot);

            if (newTimeslot != null) {
                newTimeslotDTO = convertTimeslotIntoTimeslotDTO(newTimeslot);
            }
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage());
        }

        return newTimeslotDTO;
    }

    @Override
    public boolean deleteTimeslotById(int timeslotId) throws ServiceException {
        boolean isDelete;
        try {
            isDelete = timeslotRepository.deleteTimeslotById(timeslotId);
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage());
        }

        return isDelete;
    }

    private TimeslotDTO convertTimeslotIntoTimeslotDTO(Timeslot timeslot) {
        return modelMapper.map(timeslot, TimeslotDTO.class);
    }

}
