package com.itrex.java.lab.service.services;

import com.itrex.java.lab.dto.*;
import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.persistence.repository.UserRepository;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.mockito.quality.Strictness;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import com.itrex.java.lab.service.hibernate.PatientServiceImpl;

import java.util.Set;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class PatientServiceImplTest {

    private final static String TEST_USER_FIRST_NAME = "test name";
    private final static String TEST_USER_LAST_NAME = "test last name";
    private final static Integer TEST_USER_AGE = 25;
    private final static String TEST_USER_EMAIL = "test@email.test";
    private final static String TEST_USER_PASSWORD = "password";
    private final static String TEST_USER_GENDER = "test gender";
    private final static Integer TEST_USER_NUMBER_PHONE = 34546674;
    private final static RoleType TEST_USER_ROLE= RoleType.PATIENT;

    @InjectMocks
    private PatientServiceImpl patientService;
    @Mock
    private UserRepository userRepository;

    @Test
    void createPatient_validData_shouldCreatePatient() {
        //given
        PatientDTO patientDTO = PatientDTO.builder()
                .firstName(TEST_USER_FIRST_NAME)
                .lastName(TEST_USER_LAST_NAME)
                .age(TEST_USER_AGE)
                .email(TEST_USER_EMAIL)
                .password(TEST_USER_PASSWORD)
                .gender(TEST_USER_GENDER)
                .phoneNum(TEST_USER_NUMBER_PHONE)
                .roles(Set.of(Role.builder().name(TEST_USER_ROLE).build()))
                .build();
        User patient = initUser(1);

        //when
        when(userRepository.add(patient)).thenReturn(patient);
        CreatePatientDTO actualPatientDTO = patientService.createPatient(patientDTO);

        //then
        assertAll(
                () -> assertEquals(patient.getFirstName(), actualPatientDTO.getFirstName()),
                () -> assertEquals(patient.getLastName(), actualPatientDTO.getLastName()),
                () -> assertEquals(patient.getAge(), actualPatientDTO.getAge()),
                () -> assertEquals(patient.getEmail(), actualPatientDTO.getEmail()),
                () -> assertEquals(patient.getPassword(), actualPatientDTO.getPassword()),
                () -> assertEquals(patient.getGender(), actualPatientDTO.getGender()),
                () -> assertEquals(patient.getPhoneNum(), actualPatientDTO.getPhoneNum()),
                () -> assertEquals(patient.getRoles(), actualPatientDTO.getRoles())
        );
    }

    @Test
    void createPatient_repositoryThrowError_shouldThrowServiceException() {
        //given
        PatientDTO patientDTO = PatientDTO.builder().build();

        //when
        when(patientService.createPatient(patientDTO)).thenThrow(new RepositoryException("some msg"));

        //then
        assertThrows(ServiceException.class, () -> patientService.createPatient(patientDTO));
    }

    @Test
    void getAllPatients_validData_shouldReturnPatientsList() {
        //given
        Integer expectedListSize = 2;
        User patient1 = initUser(1);
        User patient2 = initUser(2);
        when(userRepository.getAllUsersByRole(RoleType.PATIENT))
                .thenReturn(Arrays.asList(patient1, patient2));

        //when
        List<PatientViewDTO> result = patientService.getAllPatients();

        //then
        assertEquals(expectedListSize, result.size());
        assertTrue(result.stream().allMatch(doctor -> doctor.getFirstName().equals(TEST_USER_FIRST_NAME) && doctor.getLastName().equals(TEST_USER_LAST_NAME)));
        verify(userRepository, times(1)).getAllUsersByRole(eq(TEST_USER_ROLE));
    }

    @Test
    void getAllPatients_repositoryThrowError_shouldThrowServiceException() {
        //given
        when(userRepository.getAllUsersByRole(RoleType.PATIENT)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(ServiceException.class, () -> patientService.getAllPatients());
    }

    @Test
    void deletePatientById_existPatient_shouldDeletePatient() {
        //given
        when(userRepository.deleteUserById(1)).thenReturn(true);
        when(userRepository.deleteUserById(not(eq(1)))).thenReturn(false);

        //when
        boolean result = patientService.deletePatient(1);

        //then
        assertTrue(result);
        verify(userRepository, times(1)).deleteUserById(eq(1));
    }

    @Test
    void deletePatientById_repositoryThrowError_shouldThrowServiceException() {
        //given
        when(userRepository.deleteUserById(1)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(ServiceException.class, () -> patientService.deletePatient(1)) ;
    }

    @Test
    void getPatientById_validData_shouldReturnThePatientById() {
        //given
        User addedPatient = initUser(1);

        when(userRepository.getUserById(1))
                .thenReturn(Optional.of(addedPatient));

        //when
        Optional<PatientViewDTO> result = patientService.getPatientById(1);

        //then
        verify(userRepository, times(1)).getUserById(eq(1));
        assertTrue(result.stream().allMatch(patient -> patient.getFirstName().equals(TEST_USER_FIRST_NAME)
                && patient.getLastName().equals(TEST_USER_LAST_NAME)
        ));
    }

    @Test
    void getPatientById_repositoryThrowError_shouldThrowServiceException() {
        //given
        when(userRepository.getUserById(1)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(ServiceException.class, () -> patientService.getPatientById(1)) ;
    }

    @Test
    void updatePatientById_repositoryThrowError_shouldThrowServiceException() {
        //given
        PatientDTO patientDTO = PatientDTO.builder().build();
        when(userRepository.getUserById(1)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(ServiceException.class, () -> patientService.updatePatient(patientDTO)) ;
    }

    private User initUser(Integer id) {
        return User.builder()
                .userId(id)
                .firstName(TEST_USER_FIRST_NAME)
                .lastName(TEST_USER_LAST_NAME)
                .age(TEST_USER_AGE)
                .email(TEST_USER_EMAIL)
                .password(TEST_USER_PASSWORD)
                .gender(TEST_USER_GENDER)
                .phoneNum(TEST_USER_NUMBER_PHONE)
                .roles(Set.of(Role.builder().name(TEST_USER_ROLE).build()))
                .build();
    }
}
