package com.itrex.java.lab.service.serviceimpl;

import java.util.Set;

import com.itrex.java.lab.dto.*;
import com.itrex.java.lab.persistence.entity.*;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.service.impl.DoctorServiceImpl;
import com.itrex.java.lab.persistence.repository.UserRepository;

import java.util.List;
import java.util.Arrays;
import java.util.Optional;
import org.mockito.quality.Strictness;
import org.mockito.junit.jupiter.MockitoSettings;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.not;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class DoctorServiceImplTest {

    private final static String TEST_USER_FIRST_NAME = "test name";
    private final static String TEST_USER_LAST_NAME = "test last name";
    private final static Integer TEST_USER_AGE = 25;
    private final static String TEST_USER_EMAIL = "test@email.test";
    private final static String TEST_USER_PASSWORD = "password";
    private final static String TEST_USER_GENDER = "test gender";
    private final static Integer TEST_USER_NUMBER_PHONE = 34546674;
    private final static RoleType TEST_USER_ROLE= RoleType.DOCTOR;

    @InjectMocks
    private DoctorServiceImpl doctorService;
    @Mock
    private UserRepository userRepository;

    @Test
    void createDoctor_validData_shouldCreateDoctor() {
        //given
        DoctorDTO doctorDTO = DoctorDTO.builder()
                .firstName(TEST_USER_FIRST_NAME)
                .lastName(TEST_USER_LAST_NAME)
                .age(TEST_USER_AGE)
                .email(TEST_USER_EMAIL)
                .password(TEST_USER_PASSWORD)
                .gender(TEST_USER_GENDER)
                .phoneNum(TEST_USER_NUMBER_PHONE)
                .roles(Set.of(Role.builder().name(TEST_USER_ROLE).build()))
                .build();
        User doctor = initUser(1);

        //when
        when(userRepository.add(doctor)).thenReturn(doctor);
        CreateDoctorDTO actualDoctorDTO = doctorService.createDoctor(doctorDTO);

        //then
        assertAll(
                () -> assertEquals(doctor.getFirstName(), actualDoctorDTO.getFirstName()),
                () -> assertEquals(doctor.getLastName(), actualDoctorDTO.getLastName()),
                () -> assertEquals(doctor.getAge(), actualDoctorDTO.getAge()),
                () -> assertEquals(doctor.getEmail(), actualDoctorDTO.getEmail()),
                () -> assertEquals(doctor.getPassword(), actualDoctorDTO.getPassword()),
                () -> assertEquals(doctor.getGender(), actualDoctorDTO.getGender()),
                () -> assertEquals(doctor.getPhoneNum(), actualDoctorDTO.getPhoneNum()),
                () -> assertEquals(doctor.getRoles(), actualDoctorDTO.getRoles())
        );
    }

    @Test
    void createDoctor_repositoryThrowError_shouldThrowServiceException() {
        //given
        DoctorDTO doctorDTO = DoctorDTO.builder().build();

        //when
        when(doctorService.createDoctor(doctorDTO)).thenThrow(new RepositoryException("some msg"));

        //then
        assertThrows(ServiceException.class, () -> doctorService.createDoctor(doctorDTO));
    }

    @Test
    void getAllDoctors_validData_shouldReturnDoctorsList() {
        //given
        Integer expectedListSize = 2;
        User doctor1 = initUser(1);
        User doctor2 = initUser(2);
        when(userRepository.getAllUsersByRole(RoleType.DOCTOR))
          .thenReturn(Arrays.asList(doctor1, doctor2));

        //when
        List<DoctorViewDTO> result = doctorService.getAllDoctors();

        //then
        assertEquals(expectedListSize, result.size());
        assertTrue(result.stream().allMatch(doctor -> doctor.getFirstName().equals(TEST_USER_FIRST_NAME)
                && doctor.getLastName().equals(TEST_USER_LAST_NAME)));
        verify(userRepository, times(1)).getAllUsersByRole(eq(TEST_USER_ROLE));
    }

    @Test
    void getAllDoctors_repositoryThrowError_shouldThrowServiceException() {
        //given
        when(userRepository.getAllUsersByRole(RoleType.DOCTOR)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(ServiceException.class, () -> doctorService.getAllDoctors());
    }

    @Test
    void deleteDoctorById_existDoctor_shouldDeleteDoctor() {
        //given
        when(userRepository.deleteUserById(1)).thenReturn(true);
        when(userRepository.deleteUserById(not(eq(1)))).thenReturn(false);

        //when
        boolean result = doctorService.deleteDoctor(1);

        //then
        assertTrue(result);
        verify(userRepository, times(1)).deleteUserById(eq(1));
    }

    @Test
    void deleteDoctorById_repositoryThrowError_shouldThrowServiceException() {
        //given
        when(userRepository.deleteUserById(1)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(ServiceException.class, () -> doctorService.deleteDoctor(1)) ;
    }

    @Test
    void getDoctorById_validData_shouldReturnTheDoctorById() {
        //given
        User addedDoctor = initUser(1);

        when(userRepository.getUserById(1))
                .thenReturn(Optional.of(addedDoctor));

        //when
        Optional<DoctorViewDTO> result = doctorService.getDoctorById(1);

        //then
        verify(userRepository, times(1)).getUserById(eq(1));
        assertTrue(result.stream().allMatch(doctor -> doctor.getFirstName().equals(TEST_USER_FIRST_NAME)
                && doctor.getLastName().equals(TEST_USER_LAST_NAME)
        ));
    }

    @Test
    void getDoctorById_repositoryThrowError_shouldThrowServiceException() {
        //given
        when(userRepository.getUserById(1)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(ServiceException.class, () -> doctorService.getDoctorById(1)) ;
    }

    @Test
    void updateDoctorById_repositoryThrowError_shouldThrowServiceException() {
        //given
        DoctorDTO doctorDTO = DoctorDTO.builder().build();
        when(userRepository.getUserById(1)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(ServiceException.class, () -> doctorService.updateDoctor(doctorDTO)) ;
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
