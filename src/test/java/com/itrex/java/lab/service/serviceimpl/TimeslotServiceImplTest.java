package com.itrex.java.lab.service.serviceimpl;

import com.itrex.java.lab.util.TimeslotConversionUtils;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.mockito.quality.Strictness;
import com.itrex.java.lab.dto.TimeslotDTO;
import com.itrex.java.lab.persistence.entity.*;
import com.itrex.java.lab.dto.CreateTimeslotDTO;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.service.impl.TimeslotServiceImpl;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.persistence.repository.TimeslotRepository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.not;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class TimeslotServiceImplTest {

    private final static Time TEST_START_TIME = new Time(12-20);
    private final static Date TEST_DATE = new Date(2021-10-10);
    private final static Integer TEST_OFFICE = 505;

    @InjectMocks
    private TimeslotServiceImpl timeslotService;
    @Mock
    private TimeslotRepository timeslotRepository;

//    @Test
//    void createTimeslot_repositoryThrowError_shouldThrowServiceException() {
//        //given
//        TimeslotDTO timeslotDTO = TimeslotDTO.builder()
//                .timeslotId(1)
//                .startTime(TEST_START_TIME)
//                .date(TEST_DATE)
//                .office(TEST_OFFICE)
//                .build();
//        Timeslot timeslot = initTimeslot(1);
//        when(timeslotRepository.add(timeslot)).thenThrow(new RepositoryException("some msg"));
//
//        //when && then
//        assertThrows(ServiceException.class, () -> timeslotService.createTimeslot(timeslotDTO));
//    }

    @Test
    void createTimeslot_validData_shouldCreateTimeslot() {
        //given
        TimeslotDTO timeslotDTO = TimeslotDTO.builder()
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
        when(timeslotRepository.add(timeslot)).thenReturn(timeslot);
        TimeslotDTO actualTimeslotDTO = timeslotService.createTimeslot(timeslotDTO);

        //then
        assertAll(
                () -> assertEquals(timeslot.getStartTime(), actualTimeslotDTO.getStartTime()),
                () -> assertEquals(timeslot.getDate(), actualTimeslotDTO.getDate()),
                () -> assertEquals(timeslot.getOffice(), actualTimeslotDTO.getOffice())
        );
    }

    @Test
    void getAllTimeslots_validData_shouldReturnTimeslotsList() {
        //given
        Integer expectedListSize = 2;
        Timeslot timeslot1 = initTimeslot(1);
        Timeslot timeslot2 = initTimeslot(2);

        when(timeslotRepository.getAllTimeslots())
                .thenReturn(Arrays.asList(timeslot1, timeslot2));

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
        when(timeslotRepository.getAllTimeslots()).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(ServiceException.class, () -> timeslotService.getAllTimeslot());
    }

    @Test
    void deleteTimeslotById_existTimeslot_shouldDeleteTimeslot() {
        //given
        when(timeslotRepository.deleteTimeslotById(1)).thenReturn(true);
        when(timeslotRepository.deleteTimeslotById(not(eq(1)))).thenReturn(false);

        //when
        boolean result = timeslotService.deleteTimeslot(1);

        //then
        assertTrue(result);
        verify(timeslotRepository, times(1)).deleteTimeslotById(eq(1));
    }

    @Test
    void deleteTimeslotById_repositoryThrowError_shouldThrowServiceException() {
        //given
        when(timeslotRepository.deleteTimeslotById(1)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(ServiceException.class, () -> timeslotService.deleteTimeslot(1));
    }

    @Test
    void getTimeslotById_validData_shouldReturnTheTimeslotById() {
        //given
        Timeslot addedTimeslot = initTimeslot(1);

        when(timeslotRepository.getTimeslotById(1))
                .thenReturn(Optional.of(addedTimeslot));

        //when
        Optional<CreateTimeslotDTO> result = timeslotService.getTimeslotById(1);

        //then
        verify(timeslotRepository, times(1)).getTimeslotById(eq(1));
        assertTrue(result.stream().allMatch(timeslot -> timeslot.getStartTime().equals(TEST_START_TIME)
                && timeslot.getDate().equals(TEST_DATE)
                && timeslot.getOffice().equals(TEST_OFFICE)));

    }

    @Test
    void getTimeslotById_repositoryThrowError_shouldThrowServiceException() {
        //given
        when(timeslotRepository.getTimeslotById(1)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(ServiceException.class, () -> timeslotService.getTimeslotById(1)) ;
    }

    @Test
    void updateTimeslotById_repositoryThrowError_shouldThrowServiceException() {
        //given
        TimeslotDTO timeslotDTO = TimeslotDTO.builder().build();
        when(timeslotRepository.getTimeslotById(1)).thenThrow(new RepositoryException("some msg"));

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
