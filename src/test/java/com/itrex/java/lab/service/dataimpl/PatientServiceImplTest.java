package com.itrex.java.lab.service.dataimpl;

import com.itrex.java.lab.dto.CreatePatientDTO;
import com.itrex.java.lab.dto.PatientDTO;
import com.itrex.java.lab.dto.PatientViewDTO;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.persistence.dataimpl.UserRepository;
import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.persistence.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
class PatientServiceImplTest {

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
        when(userRepository.save(patient)).thenReturn(patient);
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
        CreatePatientDTO patientDTO = CreatePatientDTO.builder().build();

        //when
        when(patientService.createPatient(patientDTO)).thenThrow(new RepositoryException("some msg"));

        //then
        assertThrows(RepositoryException.class, () -> patientService.createPatient(patientDTO));
    }

    @Test
    void getAllPatients_validData_shouldReturnPatientsList() {
        //given
        Integer expectedListSize = 2;
        User firstPatient = initUser(1);
        User secondPatient = initUser(2);
        Pageable pageable = PageRequest.of(1, 2, Sort.by("lastName").descending());
        when(userRepository.findAllByRolesName(RoleType.PATIENT, pageable))
                .thenReturn(new PageImpl<>(Arrays.asList(firstPatient, secondPatient)));

        //when
        Page<PatientViewDTO> result = patientService.getAllPatients(pageable);

        //then
        assertEquals(expectedListSize, result.getSize());
        assertTrue(result.stream().allMatch(doctor -> doctor.getFirstName().equals(TEST_USER_FIRST_NAME) &&
                doctor.getLastName().equals(TEST_USER_LAST_NAME)));
        verify(userRepository, times(1)).findAllByRolesName(eq(TEST_USER_ROLE), pageable);
    }

    @Test
    void getAllPatients_repositoryThrowError_shouldThrowServiceException() {
        //given
        Pageable pageable = PageRequest.of(1, 2, Sort.by("lastName").descending());
        when(userRepository.findAllByRolesName(RoleType.PATIENT, pageable)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(RepositoryException.class, () -> patientService.getAllPatients(pageable));
    }

    @Test
    void deletePatientById_existPatient_shouldDeletePatient() {
        //given
        Integer patientId = 1;

        //when
        boolean result = patientService.deletePatient(patientId);

        //then
        assertTrue(result);
        verify(userRepository, times(patientId)).deleteById(eq(1));
    }

    @Test
    void getPatientById_validData_shouldReturnThePatientById() {
        //given
        User addedPatient = initUser(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(addedPatient));

        //when
        Optional<PatientViewDTO> result = patientService.getPatientById(1);

        //then
        verify(userRepository, times(1)).findById(eq(1));
        assertTrue(result.stream().allMatch(patient -> patient.getFirstName().equals(TEST_USER_FIRST_NAME)
                && patient.getLastName().equals(TEST_USER_LAST_NAME)
        ));
    }

    @Test
    void getPatientById_repositoryThrowError_shouldThrowServiceException() {
        //given
        when(userRepository.findById(1)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(RepositoryException.class, () -> patientService.getPatientById(1)) ;
    }

    @Test
    void updatePatientById_repositoryThrowError_shouldThrowServiceException() {
        //given
        PatientDTO patientDTO = PatientDTO.builder().build();
        when(userRepository.findById(1)).thenThrow(new RepositoryException("some msg"));

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
