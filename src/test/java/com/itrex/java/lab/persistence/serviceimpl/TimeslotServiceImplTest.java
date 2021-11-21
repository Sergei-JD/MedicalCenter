package com.itrex.java.lab.persistence.serviceimpl;

import com.itrex.java.lab.converter.TimeslotConverter;
import com.itrex.java.lab.converter.UserConverter;
import com.itrex.java.lab.dto.CreateTimeslotDTO;
import com.itrex.java.lab.dto.DoctorViewDTO;
import com.itrex.java.lab.dto.PatientViewDTO;
import com.itrex.java.lab.dto.TimeslotDTO;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.persistence.entity.Timeslot;
import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.persistence.repository.RoleRepository;
import com.itrex.java.lab.persistence.repository.TimeslotRepository;
import com.itrex.java.lab.persistence.repository.UserRepository;
import com.itrex.java.lab.service.impl.TimeslotServiceImpl;
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
public class TimeslotServiceImplTest {

    @InjectMocks
    private TimeslotServiceImpl timeslotService;
    @Mock
    private TimeslotRepository timeslotRepository;
    @Mock
    private TimeslotConverter timeslotConverter;

    @Test
    void getAllTimeslots_validData_shouldReturnTimeslotsList() {
        //given
        Integer expectedListSize = 2;
        Timeslot timeslot = Timeslot.builder().build();
        TimeslotDTO timeslotDTO = TimeslotDTO.builder().build();

        //when
        when(timeslotConverter.toTimeslotDTO(timeslot)).thenReturn(timeslotDTO);
        when(timeslotRepository.getAllTimeslots()).thenReturn(Arrays.asList(timeslot, timeslot));
        List<CreateTimeslotDTO> actualList = timeslotService.getAllTimeslot();

        //then
        assertEquals(expectedListSize, actualList.size());
    }

    @Test
    void getTimeslotById_invalidData_shouldReturnEmptyOptional() {
        //given
        Integer timeslotId = -1;

        //when
        when(timeslotRepository.getTimeslotById(timeslotId)).thenReturn(Optional.empty());
        Optional<CreateTimeslotDTO> actualOptTimeslot = timeslotService.getTimeslotById(timeslotId);

        // then
        assertTrue(actualOptTimeslot.isEmpty());
    }

    @Test
    void deleteTimeslotById_existTimeslot_shouldDeleteTimeslot() {
        //given
        Integer timeslotId = 1;

        //when
        when(timeslotRepository.deleteTimeslotById(timeslotId)).thenReturn(true);

        //then
        assertTrue(timeslotService.deleteTimeslot(timeslotId));
    }

    @Test
    void deleteTimeslotById_invalidData_shouldNotDeleteTimeslot() {
        //given
        Integer timeslotId = -1;

        //when
        when(timeslotRepository.deleteTimeslotById(timeslotId)).thenReturn(false);

        //then
        assertFalse(timeslotService.deleteTimeslot(timeslotId));
    }

}
