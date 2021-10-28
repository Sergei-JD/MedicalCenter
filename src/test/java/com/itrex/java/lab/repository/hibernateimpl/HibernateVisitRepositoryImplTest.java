package com.itrex.java.lab.repository.hibernateimpl;

import com.itrex.java.lab.entity.Role;
import com.itrex.java.lab.entity.User;
import com.itrex.java.lab.entity.Visit;
import com.itrex.java.lab.repository.BaseRepositoryTest;
import com.itrex.java.lab.repository.RepositoryException;
import com.itrex.java.lab.repository.VisitRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HibernateVisitRepositoryImplTest extends BaseRepositoryTest {

    private final VisitRepository visitRepository;

    HibernateVisitRepositoryImplTest() {
        super();
        visitRepository = new HibernateVisitRepositoryImpl(getSessionFactory().openSession());
    }


    @Test
    void getAllVisit_shouldReturnTheNumberOfAllVisits() throws RepositoryException {
        //given
        Integer allVisitCount = 3;
        List<Visit> allVisits = visitRepository.getAllVisit();

        //when && then
        assertEquals(allVisits.size(), allVisitCount);
    }

    @Test
    void getVisitById_shouldReturnTheUserById() throws RepositoryException {
        //given
        int visitId = 1;

        //when
        Visit result = visitRepository.getVisitById(visitId);

        //then
        assertAll(
                () -> assertEquals(1, result.getVisitId()),
                () -> assertEquals("the patient is recovering", result.getComment())
        );
    }

    @Test
    void addVisit_notValidData_shouldThrowRepositoryException() {
        //given
        Visit newVisit = new Visit();

        //when && then
        assertThrows(RepositoryException.class, () -> visitRepository.add(newVisit));
    }

    @Test
    void deleteVisitById_notExistVisit_shouldNotDeleteVisit() throws RepositoryException {
        //given
        int visitId = -1;
        List<Visit> allVisitsBeforeDelete = visitRepository.getAllVisit();
        assertTrue(allVisitsBeforeDelete.stream().noneMatch(visit -> visit.getVisitId().equals(visitId)));

        //when
        boolean result = visitRepository.deleteVisitById(visitId);

        //then
        assertFalse(result);
        List<Visit> allVisitsAfterDelete = visitRepository.getAllVisit();
        assertTrue(allVisitsBeforeDelete.size() == allVisitsAfterDelete.size()
                && allVisitsBeforeDelete.containsAll(allVisitsAfterDelete));
    }

//    @Test
//    void deleteVisitByID_existVisit_shouldDeleteVisit() throws RepositoryException {
//        //given
//        Integer visitId = 2;
//        List<Visit> allVisits = visitRepository.getAllVisit();
//        assertTrue(allVisits.stream().anyMatch(visit -> visit.getVisitId().equals(visitId)));
//
//        //when
//        boolean result = visitRepository.deleteVisitById(visitId);
//
//        //then
//        assertTrue(result);
//        allVisits = visitRepository.getAllVisit();
//        assertTrue(allVisits.stream().noneMatch(visit -> visit.getVisitId().equals(visitId)));
//    }

}