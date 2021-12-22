package com.itrex.java.lab.service;

import com.itrex.java.lab.dto.VisitDTO;
import com.itrex.java.lab.dto.VisitViewDTO;
import com.itrex.java.lab.dto.CreateVisitDTO;
import com.itrex.java.lab.dto.VisitHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VisitService {
    Page<VisitViewDTO> getAllVisit(Pageable pageable);

    Page<VisitViewDTO>getAllFreeVisits(Pageable pageable);

    List<VisitViewDTO> getAllFreeVisitsForDoctorById(int doctorId);

    List<VisitViewDTO>getAllVisitsForPatientDyId(int patientId);

    Optional<VisitViewDTO> getVisitById(int visitId);

    VisitDTO createVisit(CreateVisitDTO visitDTO);

    VisitDTO updateVisit(VisitDTO visitDTO);

    VisitHistoryDTO updateVisitHistory(VisitDTO visitDTO);

    boolean deleteVisit(int visitId);

}
