package com.itrex.java.lab.service.serviceimpl;

import org.modelmapper.ModelMapper;
import com.itrex.java.lab.dto.VisitDTO;
import com.itrex.java.lab.entity.Visit;
import com.itrex.java.lab.service.VisitService;
import com.itrex.java.lab.repository.VisitRepository;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.exception.RepositoryException;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final ModelMapper modelMapper;

    public VisitServiceImpl(@Qualifier("hibernateVisitRepositoryImpl") VisitRepository visitRepository, ModelMapper modelMapper) {
        this.visitRepository = visitRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<VisitDTO> getAllVisits() throws ServiceException {
        List<VisitDTO> allVisitDTOList = new ArrayList<>();
        try {
            List<Visit> visits = visitRepository.getAllVisits();

            if (visits.size() != 0) {
                allVisitDTOList = visits.stream()
                        .map(this::convertVisitIntoVisitDTO)
                        .collect(Collectors.toList());
            }
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage());
        }

        return allVisitDTOList;
    }

    @Override
    public Optional<VisitDTO> getVisitById(int visitId) throws ServiceException {
        VisitDTO visitDTO = null;
        try {
            Optional<Visit> visit = visitRepository.getVisitById(visitId);

            if (visit.isPresent()) {
                visitDTO = convertVisitIntoVisitDTO(visit.get());
            }
        } catch (RepositoryException ex) {
            throw  new ServiceException(ex.getMessage());
        }

        return Optional.ofNullable(visitDTO);
    }

    @Override
    public VisitDTO add(Visit visit) throws ServiceException {
        VisitDTO newVisitDTO = null;
        try {
            Visit newVisit = visitRepository.add(visit);

            if (newVisit != null) {
                newVisitDTO = convertVisitIntoVisitDTO(newVisit);
            }
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage());
        }

        return newVisitDTO;
    }

    @Override
    public boolean deleteVisitById(int visitId) throws ServiceException {
        boolean isDelete;
        try {
            isDelete = visitRepository.deleteVisitById(visitId);
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage());
        }

        return isDelete;
    }

    private VisitDTO convertVisitIntoVisitDTO(Visit visit) {
        return modelMapper.map(visit, VisitDTO.class);
    }

}
