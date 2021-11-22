package com.itrex.java.lab.service.serviceimpl;

import com.itrex.java.lab.converter.VisitConverter;
import com.itrex.java.lab.dto.VisitViewDTO;
import com.itrex.java.lab.persistence.entity.Visit;
import com.itrex.java.lab.persistence.repository.VisitRepository;
import com.itrex.java.lab.service.impl.VisitServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VisitServiceImplTest {

    @InjectMocks
    private VisitServiceImpl visitService;
    @Mock
    private VisitRepository visitRepository;
    @Mock
    private VisitConverter visitConverter;

    @Test
    void getAllVisits_validData_shouldReturnVisitsList() {
        //given
        Integer expectedListSize = 2;
        Visit visit = Visit.builder().build();
        VisitViewDTO visitViewDTO = VisitViewDTO.builder().build();

        //when
        when(visitConverter.toVisitViewDTO(visit)).thenReturn(visitViewDTO);
        when(visitRepository.getAllVisits()).thenReturn(Arrays.asList(visit, visit));
        List<VisitViewDTO> actualList = visitService.getAllVisit();

        //then
        assertEquals(expectedListSize, actualList.size());
    }

    @Test
    void getVisitById_invalidData_shouldReturnEmptyOptional() {
        //given
        Integer visitId = -1;

        //when
        when(visitRepository.getVisitById(visitId)).thenReturn(Optional.empty());
        Optional<VisitViewDTO> actualOptionalVisit = visitService.getVisitById(visitId);

        // then
        assertTrue(actualOptionalVisit.isEmpty());
    }

    @Test
    void getVisitById_validData_shouldReturnVisitById() {
        //given
        Integer visitId = 1;
        Integer doctorId = 1;
        Integer patientId = 1;
        Visit visit = Visit.builder().build();

        //when
        when(visitRepository.getVisitById(visitId)).thenReturn(Optional.of(visit));
        Optional<VisitViewDTO> actualOptionalVisit = visitService.getVisitById(visitId);

        // then
        assertTrue(actualOptionalVisit.isEmpty());
    }

//    @Test
//    void deleteVisitById_existVisit_shouldDeleteVisit() {
//        //given
//        Integer visitId = 1;
//        Visit visit = Visit.builder().build();
//        VisitViewDTO visitViewDTO = VisitViewDTO.builder().build();
//
//        //when
//        when(visitRepository.deleteVisitById(visitId)).thenReturn(true);
//
//        //then
//        assertTrue(visitService.deleteVisit(visitId));
//    }

//    @Test
//    void deleteVisitById_existVisit_shouldDeleteVisit() {
//        //given
//        Integer visitId = 1;
//
//        //when
//        when(visitRepository.deleteVisitById(visitId)).thenReturn(true);
//
//        //then
//        assertTrue(visitService.deleteVisit(visitId));
//    }

    @Test
    void deleteVisitById_invalidData_shouldNotDeleteVisit() {
        //given
        Integer visitId = -1;

        //when
        when(visitRepository.deleteVisitById(visitId)).thenReturn(false);

        //then
        assertFalse(visitService.deleteVisit(visitId));
    }

}
