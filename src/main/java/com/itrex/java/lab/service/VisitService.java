package com.itrex.java.lab.service;

import com.itrex.java.lab.dto.VisitDTO;
import com.itrex.java.lab.entity.Visit;
import com.itrex.java.lab.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface VisitService {

    List<VisitDTO> getAllVisits() throws ServiceException;

    Optional<VisitDTO> getVisitById(int visitId) throws ServiceException;

    VisitDTO add(Visit visit) throws ServiceException;

    boolean deleteVisitById(int visitId) throws ServiceException;

}
