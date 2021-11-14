package com.itrex.java.lab.persistence.hibernateimpl;

import org.junit.jupiter.api.Test;
import com.itrex.java.lab.persistence.entity.Timeslot;
import com.itrex.java.lab.persistence.BaseRepositoryTest;
import com.itrex.java.lab.exception.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.itrex.java.lab.persistence.repository.TimeslotRepository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HibernateTimeslotRepositoryImplTest extends BaseRepositoryTest {

    @Autowired
    @Qualifier("hibernateTimeslotRepositoryImpl")
    private TimeslotRepository timeslotRepository;

    @Test
    void getAllTimeslot_shouldReturnTheNumberOfAllTimeslots() throws RepositoryException {
        //given
        Timeslot newTimeslot1 = Timeslot.builder()
                .startTime(new Time(16-20))
                .date(new Date(2021-10-10))
                .office(505)
                .build();

        Timeslot newTimeslot2 = Timeslot.builder()
                .startTime(new Time(17-20))
                .date(new Date(2021-10-10))
                .office(101)
                .build();

        timeslotRepository.add(newTimeslot1);
        timeslotRepository.add(newTimeslot2);

        //when && then
        assertEquals(timeslotRepository.getAllTimeslots().size(), 2);
    }

    @Test
    void getTimeslotById_shouldReturnTheTimeslotById() throws RepositoryException {
        //given
        Timeslot newTimeslot = Timeslot.builder()
                .startTime(new Time(16-20))
                .date(new Date(2021-10-10))
                .office(505)
                .build();

        timeslotRepository.add(newTimeslot);

        //when
        Optional<Timeslot> result = timeslotRepository.getTimeslotById(newTimeslot.getTimeslotId());

        //then
        assertEquals(505, result.get().getOffice());
    }

    @Test
    void addTimeslot_notValidData_officeNull_shouldThrowRepositoryException() {
        //given
        Timeslot newTimeslot = Timeslot.builder()
                .startTime(new Time(12-20))
                .date(new Date(2021-10-10))
                .build();

        //when && then
        assertThrows(RepositoryException.class, () -> timeslotRepository.add(newTimeslot));
    }

    @Test
    void addTimeslot_notValidData_dateNull_shouldThrowRepositoryException() {
        //given
        Timeslot newTimeslot = Timeslot.builder()
                .startTime(new Time(12-20))
                .office(505)
                .build();

        //when && then
        assertThrows(RepositoryException.class, () -> timeslotRepository.add(newTimeslot));
    }

    @Test
    void addTimeslot_notValidData_timeNull_shouldThrowRepositoryException() {
        //given
        Timeslot newTimeslot = Timeslot.builder()
                .date(new Date(2021-10-10))
                .office(505)
                .build();

        //when && then
        assertThrows(RepositoryException.class, () -> timeslotRepository.add(newTimeslot));
    }

    @Test
    void addTimeslot_ValidData_shouldAddTimeslot() throws RepositoryException {
        //given
        Timeslot newTimeslot = Timeslot.builder()
                .startTime(new Time(12-20))
                .date(new Date(2021-10-10))
                .office(505).build();

        //when
        Timeslot result = timeslotRepository.add(newTimeslot);

        //then
        assertAll(
                () -> assertEquals(new Time(12-20), result.getStartTime()),
                () -> assertEquals(new Date(2021-10-10), result.getDate()),
                () -> assertEquals(505, result.getOffice())
        );
    }

    @Test
    void deleteTimeslotById_notExistTimeslot_shouldNotDeleteTimeslot() throws RepositoryException {
        //given
        int timeslotId = -1;
        Timeslot newTimeslot = Timeslot.builder()
                .startTime(new Time(12-20))
                .date(new Date(2021-10-10))
                .office(505)
                .build();

        timeslotRepository.add(newTimeslot);

        List<Timeslot> allTimeslotsBeforeDelete = timeslotRepository.getAllTimeslots();
        assertTrue(allTimeslotsBeforeDelete.stream().noneMatch(timeslot -> timeslot.getTimeslotId().equals(timeslotId)));

        //when
        boolean result = timeslotRepository.deleteTimeslotById(timeslotId);

        //then
        assertFalse(result);

        List<Timeslot> allTimeslotsAfterDelete = timeslotRepository.getAllTimeslots();
        assertTrue(allTimeslotsBeforeDelete.size() == allTimeslotsAfterDelete.size()
                && allTimeslotsBeforeDelete.containsAll(allTimeslotsAfterDelete));
    }

    @Test
    void deleteTimeslotById_existTimeslot_shouldDeleteTimeslot() throws RepositoryException {
        //given
        Timeslot newTimeslot = Timeslot.builder()
                .startTime(new Time(12-20))
                .date(new Date(2021-10-10))
                .office(505)
                .build();

        timeslotRepository.add(newTimeslot);

        //when
        boolean result = timeslotRepository.deleteTimeslotById(newTimeslot.getTimeslotId());

        //then
        assertTrue(result);
    }
}
