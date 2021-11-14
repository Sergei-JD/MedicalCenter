package com.itrex.java.lab.converter;

import com.itrex.java.lab.dto.VisitDTO;
import com.itrex.java.lab.dto.VisitViewDTO;
import com.itrex.java.lab.dto.CreateVisitDTO;
import com.itrex.java.lab.dto.VisitHistoryDTO;
import com.itrex.java.lab.persistence.entity.Visit;

public interface VisitConverter {

    Visit toVisit(CreateVisitDTO createVisitDTO);

    VisitDTO toVisitDTO(Visit visit);

    VisitHistoryDTO toVisitHistoryDTO(Visit visit);

    VisitViewDTO toVisitViewDTO(Visit visit);

}
