package com.itrex.java.lab.repository.hibernateimpl;

import com.itrex.java.lab.entity.Timeslot;
import com.itrex.java.lab.repository.BaseRepositoryTest;
import com.itrex.java.lab.repository.RepositoryException;
import com.itrex.java.lab.repository.TimeslotRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HibernateTimeslotRepositoryImplTest extends BaseRepositoryTest {

    private final TimeslotRepository timeslotRepository;

    HibernateTimeslotRepositoryImplTest() {
        timeslotRepository = new HibernateTimeslotRepositoryImpl(getSessionFactory().openSession());
    }


    @Test
    void getAllTimeslot_shouldReturnTheNumberOfAllTimeslots() throws RepositoryException {
        //given
        Integer allTimeslotCount = 3;
        List<Timeslot> allTimeslots = timeslotRepository.getAllTimeslot();

        //when && then
        assertEquals(allTimeslots.size(), allTimeslotCount);
    }

    @Test
    void getTimeslotById_shouldReturnTheTimeslotById() throws RepositoryException {
        //given
        int timeslotId = 1;

        //when
        Timeslot result = timeslotRepository.getTimeslotByID(timeslotId);

        //then
        assertAll(
                () -> assertEquals(1, result.getTimeslotId()),
                () -> assertEquals(308, result.getOffice())
        );
    }

    @Test
    void addTimeslot_notValidData_shouldThrowRepositoryException() {
        //given
        Timeslot newTimeslot = new Timeslot();

        //when && then
        assertThrows(RepositoryException.class, () -> timeslotRepository.add(newTimeslot));
    }

    @Test
    void deleteTimeslotById_notExistTimeslot_shouldNotDeleteTimeslot() throws RepositoryException {
        //given
        int timeslotId = -1;
        List<Timeslot> allTimeslotsBeforeDelete = timeslotRepository.getAllTimeslot();
        assertTrue(allTimeslotsBeforeDelete.stream().noneMatch(timeslot -> timeslot.getTimeslotId().equals(timeslotId)));

        //when
        boolean result = timeslotRepository.deleteTimeslotById(timeslotId);

        //then
        assertFalse(result);
        List<Timeslot> allTimeslotsAfterDelete = timeslotRepository.getAllTimeslot();
        assertTrue(allTimeslotsBeforeDelete.size() == allTimeslotsAfterDelete.size()
                && allTimeslotsBeforeDelete.containsAll(allTimeslotsAfterDelete));
    }

//    @Test
//    void deleteTimeslotByID_existTimeslot_shouldDeleteTimeslot() throws RepositoryException {
//        //given
//        Integer timeslotId = 2;
//        List<Timeslot> allTimeslots = timeslotRepository.getAllTimeslot();
//        assertTrue(allTimeslots.stream().anyMatch(timeslot -> timeslot.getTimeslotId().equals(timeslotId)));
//
//        //when
//        boolean result = timeslotRepository.deleteTimeslotById(timeslotId);
//
//        //then
//        assertTrue(result);
//        allTimeslots = timeslotRepository.getAllTimeslot();
//        assertTrue(allTimeslots.stream().noneMatch(timeslot -> timeslot.getTimeslotId().equals(timeslotId)));
//    }

}