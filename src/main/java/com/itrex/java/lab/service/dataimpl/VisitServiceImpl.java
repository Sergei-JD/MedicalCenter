package com.itrex.java.lab.service.dataimpl;

import com.itrex.java.lab.dto.VisitDTO;
import com.itrex.java.lab.dto.VisitViewDTO;
import com.itrex.java.lab.dto.CreateVisitDTO;
import com.itrex.java.lab.dto.VisitHistoryDTO;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.persistence.dataimpl.UserRepository;
import com.itrex.java.lab.persistence.dataimpl.VisitRepository;
import com.itrex.java.lab.persistence.dataimpl.TimeslotRepository;
import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.persistence.entity.Visit;
import com.itrex.java.lab.persistence.entity.Timeslot;
import com.itrex.java.lab.service.VisitService;
import com.itrex.java.lab.util.VisitConversionUtils;
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
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;

    private final UserRepository userRepository;

    private final TimeslotRepository timeslotRepository;

    @Override
    public Page<VisitViewDTO> getAllVisit(Pageable pageable) {
        Page<Visit> pageVisits = visitRepository.findAll(pageable);

        List<VisitViewDTO> visits = pageVisits.stream()
                .map(VisitConversionUtils::toVisitViewDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(visits);
    }

    @Override
    public Page<VisitViewDTO> getAllFreeVisits(Pageable pageable) {
        Page<Visit> pageVisits = visitRepository.findAll(pageable);

        List<VisitViewDTO> visits = pageVisits.stream()
                .filter(visit -> (visit.getDoctor() == null && visit.getPatient() == null))
                .map(VisitConversionUtils::toVisitViewDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(visits);
    }

    @Override
    public List<VisitViewDTO> getAllFreeVisitsForDoctorById(int doctorId) {
        List<Visit> visits = visitRepository.findAll();

        return visits.stream()
                .filter(visit -> (visit.getDoctor() != null
                        && visit.getDoctor().getUserId() == doctorId
                        && visit.getPatient() == null))
                .map(VisitConversionUtils::toVisitViewDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VisitViewDTO> getAllVisitsForPatientDyId(int patientId) {
        List<Visit> visits = visitRepository.findAll();

        return visits.stream()
                .filter(visit -> (visit.getPatient() != null
                        && visit.getPatient().getUserId() == patientId))
                .map(VisitConversionUtils::toVisitViewDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<VisitViewDTO> getVisitById(int visitId) {
        VisitViewDTO visitDTO = null;

        Optional<Visit> visit = visitRepository.findById(visitId);
        if (visit.isPresent()) {
            visitDTO = VisitConversionUtils.toVisitViewDTO(visit.get());
        }

        return Optional.ofNullable(visitDTO);
    }

    @Override
    @Transactional
    public VisitDTO createVisit(CreateVisitDTO visitDTO) {
        User doctor = userRepository.findById(visitDTO.getDoctorId())
                .orElseThrow(() -> new ServiceException("Failed to update visit. No such doctor"));
        User patient = userRepository.findById(visitDTO.getPatientId())
                .orElseThrow(() -> new ServiceException("Failed to update visit. No such patient"));
        Timeslot timeslot = timeslotRepository.findById(visitDTO.getTimeslotId())
                .orElseThrow(() -> new ServiceException("Failed to update visit. No such timeslot"));

        Visit newVisit = Visit.builder()
                .doctor(doctor)
                .patient(patient)
                .timeslot(timeslot)
                .comment(visitDTO.getComment())
                .build();

        return VisitConversionUtils.toVisitDTO(visitRepository.save(newVisit));
    }

    @Override
    @Transactional
    public VisitDTO updateVisit(VisitDTO visitDTO) {
        if (visitDTO.getTimeslotId() == null) {
            throw new ServiceException("Failed to update visit. Not valid visitDTO.");
        }

        Visit visit = visitRepository.findById(visitDTO.getVisitId())
                .orElseThrow(() -> new ServiceException("Failed to update visit no such visit"));

        User doctor = userRepository.findById(visitDTO.getDoctorId())
                .orElseThrow(() -> new ServiceException("Failed to update visit. No such doctor"));
        User patient = userRepository.findById(visitDTO.getPatientId())
                .orElseThrow(() -> new ServiceException("Failed to update visit. No such patient"));
        Timeslot timeslot = timeslotRepository.findById(visitDTO.getTimeslotId())
                .orElseThrow(() -> new ServiceException("Failed to update visit. No such timeslot"));

        visit.setDoctor(doctor);
        visit.setPatient(patient);
        visit.setTimeslot(timeslot);
        visit.setComment(visitDTO.getComment());

        visitRepository.save(visit);

        return VisitConversionUtils.toVisitDTO(visit);
    }

    @Override
    @Transactional
    public VisitHistoryDTO updateVisitHistory(VisitDTO visitDTO) {
        if (visitDTO.getTimeslotId() == null) {
            throw new ServiceException("Failed to update visit history. Not valid visitDTO.");
        }

        Visit visit = visitRepository.findById(visitDTO.getVisitId())
                .orElseThrow(() -> new ServiceException("Failed to update visit history no such visit"));

        visit.setComment(visitDTO.getComment());

        visitRepository.save(visit);

        return VisitConversionUtils.toVisitHistoryDTO(visit);
    }

    @Override
    @Transactional
    public boolean deleteVisit(int visitId) {
        visitRepository.deleteById(visitId);

        return visitRepository.findById(visitId).isEmpty();
    }

}
