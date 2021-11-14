package com.itrex.java.lab.service;

import com.itrex.java.lab.dto.VisitDTO;
import com.itrex.java.lab.dto.VisitViewDTO;
import com.itrex.java.lab.dto.CreateVisitDTO;
import com.itrex.java.lab.dto.VisitHistoryDTO;

import java.util.List;

public interface VisitService {

    void createVisit(CreateVisitDTO visitDTO);

    boolean deleteVisit(int visitId);

    List<VisitViewDTO> getAllVisit();

    VisitViewDTO getVisitById(int visitId);

    VisitDTO updateVisit(VisitDTO visitDTO);

    VisitHistoryDTO updateVisitHistory(VisitDTO visitDTO);

}
