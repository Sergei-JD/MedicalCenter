package com.itrex.java.lab.persistence.serviceimpl;

import com.itrex.java.lab.converter.UserConverter;
import com.itrex.java.lab.dto.DoctorViewDTO;
import com.itrex.java.lab.dto.PatientViewDTO;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.persistence.repository.RoleRepository;
import com.itrex.java.lab.persistence.repository.UserRepository;

import com.itrex.java.lab.service.impl.PatientServiceImpl;
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
public class PatientServiceImplTest {

    @InjectMocks
    private PatientServiceImpl patientService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserConverter userConverter;

    @Test
    void getAllPatients_validData_shouldReturnPatientsList() {
        //given
        Integer expectedListSize = 2;
        User patient = User.builder().build();
        PatientViewDTO patientViewDTO = PatientViewDTO.builder().build();

        //when
        when(userConverter.toPatientViewDTO(patient)).thenReturn(patientViewDTO);
        when(userRepository.getAllUsersByRole(RoleType.PATIENT))
                .thenReturn(Arrays.asList(patient, patient));
        List<PatientViewDTO> actualList = patientService.getAllPatients();

        //then
        assertEquals(expectedListSize, actualList.size());
    }

    @Test
    void getPatientById_invalidData_shouldReturnEmptyOptional() {
        //given
        Integer patientId = -1;

        //when
        when(userRepository.getUserById(patientId)).thenReturn(Optional.empty());
        Optional<PatientViewDTO> actualOptionalPatient = patientService.getPatientById(patientId);

        // then
        assertTrue(actualOptionalPatient.isEmpty());
    }

    @Test
    void deletePatientById_existDoctor_shouldDeleteDoctor() {
        //given
        Integer patientId = 1;

        //when
        when(userRepository.deleteUserById(patientId)).thenReturn(true);

        //then
        assertTrue(patientService.deletePatient(patientId));
    }

    @Test
    void deletePatientById_invalidDate_shouldNotDeleteDoctor() {
        //given
        Integer patientId = -1;

        //when
        when(userRepository.deleteUserById(patientId)).thenReturn(false);

        //then
        assertFalse(patientService.deletePatient(patientId));
    }

}
