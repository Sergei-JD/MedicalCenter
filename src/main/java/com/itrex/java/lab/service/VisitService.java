package com.itrex.java.lab.service;

import com.itrex.java.lab.dto.VisitDTO;
import com.itrex.java.lab.dto.CreateVisitDTO;

import java.util.List;

public interface VisitService {

    void createVisit(CreateVisitDTO visitDTO);

    boolean deleteVisit(int visitId);

    List<VisitDTO> getAllVisit();

    VisitDTO getVisitById(int visitId);

}
