package com.itrex.java.lab.service.impl;

import com.itrex.java.lab.dto.CreateVisitDTO;
import com.itrex.java.lab.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.itrex.java.lab.dto.VisitDTO;
import org.springframework.stereotype.Service;
import com.itrex.java.lab.service.VisitService;
import com.itrex.java.lab.persistence.entity.Visit;
import com.itrex.java.lab.converter.VisitConverter;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.persistence.repository.VisitRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final VisitConverter visitConverter;

    @Override
    public void createVisit(CreateVisitDTO visitDTO) {
        Visit visit = visitConverter.toVisit(visitDTO);

        visitRepository.add(visit);
    }

    @Override
    public List<VisitDTO> getAllVisit() {
        try {
            List<Visit> visits = visitRepository.getAllVisits();

            return visits.stream()
                    .map(visitConverter::toVisitDTO)
                    .collect(Collectors.toList());
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to get all visits.\n" + ex);
        }
    }

    @Override
    public VisitDTO getVisitById(int visitId) {
        VisitDTO visitDTO = null;
        try {
            Optional<Visit> visit = visitRepository.getVisitById(visitId);
            if (visit.isPresent()) {
                visitDTO = visitConverter.toVisitDTO(visit.get());
            }
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to get visit by id " + visitId + ".\n" + ex);
        }

        return visitDTO;
    }

    @Override
    public boolean deleteVisit(int visitId) {
        try {
            return visitRepository.deleteVisitById(visitId);
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to delete visit by id" + visitId + ".\n" + ex);
        }
    }
}
