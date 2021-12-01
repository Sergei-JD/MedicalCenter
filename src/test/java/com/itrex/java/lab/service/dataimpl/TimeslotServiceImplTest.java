package com.itrex.java.lab.service.dataimpl;

import com.itrex.java.lab.dto.CreateTimeslotDTO;
import com.itrex.java.lab.dto.TimeslotDTO;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.persistence.dataimpl.TimeslotRepository;
import com.itrex.java.lab.persistence.entity.Timeslot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
class TimeslotServiceImplTest {

    private final static Instant TEST_START_TIME = Instant.parse("2021-04-09T15:30:45.123Z");
    private final static Instant TEST_DATE = Instant.parse("2021-04-09T15:30:45.123Z");
    private final static Integer TEST_OFFICE = 505;

    @InjectMocks
    private TimeslotServiceImpl timeslotService;

    @Mock
    private TimeslotRepository timeslotRepository;

    @Test
    void createTimeslot_validData_shouldCreateTimeslot() {
        //given
        CreateTimeslotDTO timeslotDTO = CreateTimeslotDTO.builder()
                .startTime(TEST_START_TIME)
                .date(TEST_DATE)
                .office(TEST_OFFICE)
                .build();
        Timeslot timeslot = Timeslot.builder()
                .timeslotId(1)
                .startTime(TEST_START_TIME)
                .date(TEST_DATE)
                .office(TEST_OFFICE)
                .build();

        //when
        when(timeslotRepository.save(timeslot)).thenReturn(timeslot);
        CreateTimeslotDTO actualTimeslotDTO = timeslotService.createTimeslot(timeslotDTO);

        //then
        assertAll(
                () -> assertEquals(timeslot.getStartTime(), actualTimeslotDTO.getStartTime()),
                () -> assertEquals(timeslot.getDate(), actualTimeslotDTO.getDate()),
                () -> assertEquals(timeslot.getOffice(), actualTimeslotDTO.getOffice())
        );
    }

    @Test
    void createTimeslot_repositoryThrowError_shouldThrowServiceException() {
        //given
        CreateTimeslotDTO timeslotDTO = CreateTimeslotDTO.builder().build();

        //when
        when(timeslotService.createTimeslot(timeslotDTO)).thenThrow(new RepositoryException("some msg"));

        //then
        assertThrows(RepositoryException.class, () -> timeslotService.createTimeslot(timeslotDTO));
    }

    @Test
    void getAllTimeslots_validData_shouldReturnTimeslotsList() {
        //given
        Integer expectedListSize = 2;
        Timeslot firstTimeslot = initTimeslot(1);
        Timeslot secondTimeslot = initTimeslot(2);

        when(timeslotRepository.findAll())
                .thenReturn(Arrays.asList(firstTimeslot, secondTimeslot));

        //when
        List<CreateTimeslotDTO> result = timeslotService.getAllTimeslot();

        //then
        assertEquals(expectedListSize, result.size());
        assertTrue(result.stream().allMatch(timeslot -> timeslot.getStartTime().equals(TEST_START_TIME)
                && timeslot.getDate().equals(TEST_DATE)
                && timeslot.getOffice().equals(TEST_OFFICE)));
    }

    @Test
    void getAllTimeslots_repositoryThrowError_shouldThrowServiceException() {
        //given
        when(timeslotRepository.findAll()).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(RepositoryException.class, () -> timeslotService.getAllTimeslot());
    }

    @Test
    void deleteTimeslotById_existTimeslot_shouldDeleteTimeslot() {
        //given
        Integer timeslotId = 1;

        //when
        boolean result = timeslotService.deleteTimeslot(timeslotId);

        //then
        assertTrue(result);
        verify(timeslotRepository, times(timeslotId)).deleteById(eq(1));
    }

    @Test
    void getTimeslotById_validData_shouldReturnTheTimeslotById() {
        //given
        Timeslot addedTimeslot = initTimeslot(1);

        when(timeslotRepository.findById(1))
                .thenReturn(Optional.of(addedTimeslot));

        //when
        Optional<CreateTimeslotDTO> result = timeslotService.getTimeslotById(1);

        //then
        verify(timeslotRepository, times(1)).findById(eq(1));
        assertTrue(result.stream().allMatch(timeslot -> timeslot.getStartTime().equals(TEST_START_TIME)
                && timeslot.getDate().equals(TEST_DATE)
                && timeslot.getOffice().equals(TEST_OFFICE)));

    }

    @Test
    void getTimeslotById_repositoryThrowError_shouldThrowServiceException() {
        //given
        when(timeslotRepository.findById(1)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(RepositoryException.class, () -> timeslotService.getTimeslotById(1)) ;
    }

    @Test
    void updateTimeslotById_repositoryThrowError_shouldThrowServiceException() {
        //given
        TimeslotDTO timeslotDTO = TimeslotDTO.builder().build();
        when(timeslotRepository.findById(1)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(ServiceException.class, () -> timeslotService.updateTimeslot(timeslotDTO)) ;
    }


    private Timeslot initTimeslot(Integer id) {
        return Timeslot.builder()
                .timeslotId(id)
                .startTime(TEST_START_TIME)
                .date(TEST_DATE)
                .office(TEST_OFFICE)
                .build();
    }

}
