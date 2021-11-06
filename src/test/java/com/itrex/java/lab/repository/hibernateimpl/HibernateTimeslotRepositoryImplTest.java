package com.itrex.java.lab.repository.hibernateimpl;

import com.itrex.java.lab.entity.Timeslot;
import com.itrex.java.lab.repository.BaseRepositoryTest;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.repository.TimeslotRepository;
import com.itrex.java.lab.repository.config.TestRepositoryConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HibernateTimeslotRepositoryImplTest extends BaseRepositoryTest {

    @Qualifier("hibernateTimeslotRepositoryImpl")
    @Autowired
    private TimeslotRepository timeslotRepository;

    @Test
    void getAllTimeslot_shouldReturnTheNumberOfAllTimeslots() throws RepositoryException {
        //given
        Timeslot newTimeslot1 = new Timeslot();
        newTimeslot1.setStartTime(new Time(16-20));
        newTimeslot1.setDate(new Date(2021-10-10));
        newTimeslot1.setOffice(505);

        Timeslot newTimeslot2 = new Timeslot();
        newTimeslot2.setStartTime(new Time(17-20));
        newTimeslot2.setDate(new Date(2021-10-10));
        newTimeslot2.setOffice(101);

        timeslotRepository.add(newTimeslot1);
        timeslotRepository.add(newTimeslot2);

        //when && then
        assertEquals(timeslotRepository.getAllTimeslots().size(), 2);
    }

    @Test
    void getTimeslotById_shouldReturnTheTimeslotById() throws RepositoryException {
        //given
        Timeslot newTimeslot = new Timeslot();
        newTimeslot.setStartTime(new Time(16-20));
        newTimeslot.setDate(new Date(2021-10-10));
        newTimeslot.setOffice(505);
        timeslotRepository.add(newTimeslot);

        //when
        Optional<Timeslot> result = timeslotRepository.getTimeslotById(newTimeslot.getTimeslotId());

        //then
        assertAll(
                () -> assertEquals(new Time(16-20), result.get().getStartTime()),
                () -> assertEquals(new Date(2021-10-10), result.get().getDate()),
                () -> assertEquals(505, result.get().getOffice())
        );
    }

    @Test
    void addTimeslot_notValidData_officeNull_shouldThrowRepositoryException() {
        //given
        Timeslot newTimeslot = new Timeslot();
        newTimeslot.setStartTime(new Time(12-20));
        newTimeslot.setDate(new Date(2021-10-10));

        //when && then
        assertThrows(RepositoryException.class, () -> timeslotRepository.add(newTimeslot));
    }

    @Test
    void addTimeslot_notValidData_dateNull_shouldThrowRepositoryException() {
        //given
        Timeslot newTimeslot = new Timeslot();
        newTimeslot.setStartTime(new Time(12-20));
        newTimeslot.setOffice(505);

        //when && then
        assertThrows(RepositoryException.class, () -> timeslotRepository.add(newTimeslot));
    }

    @Test
    void addTimeslot_notValidData_timeNull_shouldThrowRepositoryException() {
        //given
        Timeslot newTimeslot = new Timeslot();
        newTimeslot.setDate(new Date(2021-10-10));
        newTimeslot.setOffice(505);

        //when && then
        assertThrows(RepositoryException.class, () -> timeslotRepository.add(newTimeslot));
    }

    @Test
    void addTimeslot_ValidData_shouldAddTimeslot() throws RepositoryException {
        //given
        Timeslot newTimeslot = new Timeslot();
        newTimeslot.setStartTime(new Time(12-20));
        newTimeslot.setDate(new Date(2021-10-10));
        newTimeslot.setOffice(505);
        timeslotRepository.add(newTimeslot);

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
        Timeslot newTimeslot = new Timeslot();
        newTimeslot.setStartTime(new Time(12-20));
        newTimeslot.setDate(new Date(2021-10-10));
        newTimeslot.setOffice(505);
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
        Timeslot newTimeslot = new Timeslot();
        newTimeslot.setStartTime(new Time(12-20));
        newTimeslot.setDate(new Date(2021-10-10));
        newTimeslot.setOffice(505);
        timeslotRepository.add(newTimeslot);

        //when
        boolean result = timeslotRepository.deleteTimeslotById(newTimeslot.getTimeslotId());

        //then
        assertTrue(result);
    }

}