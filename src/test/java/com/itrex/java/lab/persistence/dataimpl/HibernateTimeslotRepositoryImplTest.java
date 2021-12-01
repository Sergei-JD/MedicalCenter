package com.itrex.java.lab.persistence.dataimpl;


import com.itrex.java.lab.persistence.entity.Timeslot;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.persistence.BaseRepositoryTest;
import com.itrex.java.lab.persistence.repository.TimeslotRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.time.Instant;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
class HibernateTimeslotRepositoryImplTest extends BaseRepositoryTest {

    private static final int OFFICE_NUMBER = "OFFICE_NUMBER".hashCode();

    @Autowired
    @Qualifier("hibernateTimeslotRepositoryImpl")
    private TimeslotRepository timeslotRepository;

    @Test
    void getAllTimeslot_shouldReturnTheNumberOfAllTimeslots() throws RepositoryException {
        //given
        int expectedTimeslotListSize = 2;

        Timeslot firstTimeslot = createTimeslot();
        timeslotRepository.add(firstTimeslot);

        Timeslot secondTimeslot = createTimeslot();
        timeslotRepository.add(secondTimeslot);

        //when && then
        assertEquals(expectedTimeslotListSize, timeslotRepository.getAllTimeslots().size());
    }

    @Test
    void getTimeslotById_shouldReturnTheTimeslotById() throws RepositoryException {
        //given
        Timeslot addTimeslot = createTimeslot();

        timeslotRepository.add(addTimeslot);

        //when
        Optional<Timeslot> result = timeslotRepository.getTimeslotById(addTimeslot.getTimeslotId());

        //then
        result.ifPresent(timeslot -> assertAll(
                () -> assertEquals(addTimeslot.getStartTime(), result.get().getStartTime()),
                () -> assertEquals(addTimeslot.getDate(), result.get().getDate()),
                () -> assertEquals(addTimeslot.getOffice(), result.get().getOffice())
        ));
    }

    @Test
    void addTimeslot_notValidData_officeNull_shouldThrowRepositoryException() {
        //given
        Timeslot addTimeslot = Timeslot.builder()
                .startTime(Instant.now())
                .date(Instant.now())
                .build();

        //when && then
        assertThrows(RepositoryException.class, () -> timeslotRepository.add(addTimeslot));
    }

    @Test
    void addTimeslot_notValidData_dateNull_shouldThrowRepositoryException() {
        //given
        Timeslot addTimeslot = Timeslot.builder()
                .startTime(Instant.now())
                .office(OFFICE_NUMBER)
                .build();

        //when && then
        assertThrows(RepositoryException.class, () -> timeslotRepository.add(addTimeslot));
    }

    @Test
    void addTimeslot_notValidData_timeNull_shouldThrowRepositoryException() {
        //given
        Timeslot addTimeslot = Timeslot.builder()
                .date(Instant.now())
                .office(OFFICE_NUMBER)
                .build();

        //when && then
        assertThrows(RepositoryException.class, () -> timeslotRepository.add(addTimeslot));
    }

    @Test
    void addTimeslot_ValidData_shouldAddTimeslot() throws RepositoryException {
        //given
        Timeslot addTimeslot = createTimeslot();

        //when
        Timeslot result = timeslotRepository.add(addTimeslot);

        //then
        assertAll(
                () -> assertEquals(addTimeslot.getStartTime(), result.getStartTime()),
                () -> assertEquals(addTimeslot.getDate(), result.getDate()),
                () -> assertEquals(addTimeslot.getOffice(), result.getOffice())
        );
    }

    @Test
    void deleteTimeslotById_notExistTimeslot_shouldNotDeleteTimeslot() throws RepositoryException {
        //given
        int timeslotId = -1;
        Timeslot addTimeslot = createTimeslot();

        timeslotRepository.add(addTimeslot);

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
        Timeslot addTimeslot = createTimeslot();

        timeslotRepository.add(addTimeslot);

        //when
        boolean result = timeslotRepository.deleteTimeslotById(addTimeslot.getTimeslotId());

        //then
        assertTrue(result);
    }

    private Timeslot createTimeslot() {
        return Timeslot.builder()
                .startTime(Instant.now())
                .date(Instant.now())
                .office(OFFICE_NUMBER)
                .build();
    }

}
