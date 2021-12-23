package com.itrex.java.lab.service.dataimpl;

import com.itrex.java.lab.dto.PatientDTO;
import com.itrex.java.lab.dto.PatientViewDTO;
import com.itrex.java.lab.dto.CreatePatientDTO;
import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.persistence.dataimpl.RoleRepository;
import com.itrex.java.lab.persistence.dataimpl.UserRepository;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.data.domain.PageRequest;

import java.util.Set;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


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

    @Mock
    private RoleRepository roleRepository;

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
    void getPatientByEmail_validData_shouldReturnThePatientByEmail() {
        //given
        User addedPatient = initUser(1);

        when(userRepository.findUserByEmail("test@email.test")).thenReturn(Optional.of(addedPatient));

        //when
        Optional<PatientDTO> result = patientService.getPatientByEmail("test@email.test");

        //then
        verify(userRepository, times(1)).findUserByEmail(eq("test@email.test"));
        assertTrue(result.stream().allMatch(patient -> patient.getFirstName().equals(TEST_USER_FIRST_NAME)
                && patient.getLastName().equals(TEST_USER_LAST_NAME)
                && patient.getEmail().equals(TEST_USER_EMAIL)
        ));
    }

    @Test
    void getPatientByEmail_repositoryThrowError_shouldThrowServiceException() {
        //given
        when(userRepository.findUserByEmail("some@email")).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(RepositoryException.class, () -> patientService.getPatientByEmail("some@email"));
    }

    @Test
    void createPatient_validData_shouldCreatePatient() {
        //given
        CreatePatientDTO patientDTO = CreatePatientDTO.builder()
                .firstName(TEST_USER_FIRST_NAME)
                .lastName(TEST_USER_LAST_NAME)
                .age(TEST_USER_AGE)
                .email(TEST_USER_EMAIL)
                .password(TEST_USER_PASSWORD)
                .gender(TEST_USER_GENDER)
                .phoneNum(TEST_USER_NUMBER_PHONE)
                .build();

        Role rolePatient = Role.builder()
                .name(RoleType.PATIENT)
                .build();

        //when
        when(roleRepository.findRoleByName(eq(RoleType.PATIENT))).thenReturn(Optional.of(rolePatient));
        CreatePatientDTO actualPatientDTO = patientService.createPatient(patientDTO);

        Optional<PatientDTO> result = patientService.getPatientByEmail(patientDTO.getEmail());

        //then
        verify(userRepository, times(1)).findUserByEmail(eq(patientDTO.getEmail()));

        assertTrue(result.stream().allMatch(patient -> patient.getFirstName().equals(actualPatientDTO.getFirstName())
                && patient.getLastName().equals(actualPatientDTO.getLastName())
                && patient.getAge().equals(actualPatientDTO.getAge())
                && patient.getEmail().equals(actualPatientDTO.getEmail())
                && patient.getPassword().equals(actualPatientDTO.getPassword())
                && patient.getGender().equals(actualPatientDTO.getGender())
        ));
    }

    @Test
    void updatePatientById_repositoryThrowError_shouldThrowServiceException() {
        //given
        PatientDTO patientDTO = PatientDTO.builder().build();
        when(userRepository.findById(1)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(ServiceException.class, () -> patientService.updatePatient(patientDTO)) ;
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
