package com.itrex.java.lab.service.serviceimpl;

import com.itrex.java.lab.dto.PatientViewDTO;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.persistence.entity.Role;
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
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
    @Mock
    private RoleRepository roleRepository;

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
