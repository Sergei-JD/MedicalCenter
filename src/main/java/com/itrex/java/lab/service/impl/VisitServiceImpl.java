package com.itrex.java.lab.service.impl;

import com.itrex.java.lab.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.itrex.java.lab.service.VisitService;
import com.itrex.java.lab.persistence.entity.Visit;
import com.itrex.java.lab.util.VisitConversionUtils;
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

    @Override
    public CreateVisitDTO createVisit(CreateVisitDTO visitDTO) {
        try {
            Visit visit = VisitConversionUtils.toVisit(visitDTO);

            visitRepository.add(visit);
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to create visit.\n" + ex);
        }

        return visitDTO;
    }

    @Override
    public List<VisitViewDTO> getAllVisit() {
        try {
            List<Visit> visits = visitRepository.getAllVisits();

            return visits.stream()
                    .map(VisitConversionUtils::toVisitViewDTO)
                    .collect(Collectors.toList());
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to get all visits.\n" + ex);
        }
    }

    @Override
    public List<VisitViewDTO> getAllFreeVisits() {
        try {
            List<Visit> visits = visitRepository.getAllVisits();

            return visits.stream()
                    .filter(visit -> (visit.getDoctor() == null && visit.getPatient() == null))
                    .map(VisitConversionUtils::toVisitViewDTO)
                    .collect(Collectors.toList());
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to get all free visits.\n" + ex);
        }
    }

    @Override
    public List<VisitViewDTO> getAllFreeVisitsForDoctorById(int doctorId) {
        try {
            List<Visit> visits = visitRepository.getAllVisits();

            return visits.stream()
                    .filter(visit -> (visit.getDoctor() != null
                            && visit.getDoctor().getUserId() == doctorId
                            && visit.getPatient() == null))
                    .map(VisitConversionUtils::toVisitViewDTO)
                    .collect(Collectors.toList());
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to get all free visits for doctor by id .\n" + ex);
        }
    }

    @Override
    public List<VisitViewDTO> getAllVisitsForPatientDyId(int patientId) {
        try {
            List<Visit> visits = visitRepository.getAllVisits();

            return visits.stream()
                    .filter(visit -> (visit.getPatient() != null
                            && visit.getPatient().getUserId() == patientId))
                    .map(VisitConversionUtils::toVisitViewDTO)
                    .collect(Collectors.toList());
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to get all visits for patient by id .\n" + ex);
        }
    }

    @Override
    public Optional<VisitViewDTO> getVisitById(int visitId) {
        VisitViewDTO visitDTO = null;
        try {
            Optional<Visit> visit = visitRepository.getVisitById(visitId);
            if (visit.isPresent()) {
                visitDTO = VisitConversionUtils.toVisitViewDTO(visit.get());
            }
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to get visit by id " + visitId + ".\n" + ex);
        }

        return Optional.ofNullable(visitDTO);
    }

    @Override
    public boolean deleteVisit(int visitId) {
        try {
            return visitRepository.deleteVisitById(visitId);
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to delete visit by id" + visitId + ".\n" + ex);
        }
    }

    @Override
    public VisitDTO updateVisit(VisitDTO visitDTO) {
        if (!isValidVisitDTO(visitDTO) || visitDTO.getTimeslotId() == null) {
            throw new ServiceException("Failed to update visit. Not valid visitDTO.");
        }
        Visit visit = visitRepository.getVisitById(visitDTO.getVisitId())
                .orElseThrow(() -> new ServiceException("Failed to update visit no such visit"));

        visit.setDoctor(visitDTO.getDoctorId());
        visit.setPatient(visitDTO.getPatientId());
        visit.setTimeslot(visitDTO.getTimeslotId());
        visit.setComment(visitDTO.getComment());

        try {
            visitRepository.update(visit);
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to update visit.\n" + ex);
        }

        return VisitConversionUtils.toVisitDTO(visit);
    }

    @Override
    public VisitHistoryDTO updateVisitHistory(VisitDTO visitDTO) {
        if (!isValidVisitDTO(visitDTO) || visitDTO.getTimeslotId() == null) {
            throw new ServiceException("Failed to update visit history. Not valid visitDTO.");
        }
        Visit visit = visitRepository.getVisitById(visitDTO.getVisitId())
                .orElseThrow(() -> new ServiceException("Failed to update visit history no such visit"));

        visit.setComment(visitDTO.getComment());

        try {
            visitRepository.update(visit);
        } catch (RepositoryException ex) {
            throw new ServiceException("Failed to update visit history.\n" + ex);
        }

        return VisitConversionUtils.toVisitHistoryDTO(visit);
    }

    private boolean isValidVisitDTO(CreateVisitDTO visitDTO) {
        return visitDTO != null && visitDTO.getDoctorId() != null && visitDTO.getPatientId() != null;
    }
}
