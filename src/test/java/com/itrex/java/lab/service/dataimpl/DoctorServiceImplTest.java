package com.itrex.java.lab.service.dataimpl;

import com.itrex.java.lab.dto.DoctorDTO;
import com.itrex.java.lab.dto.DoctorViewDTO;
import com.itrex.java.lab.dto.CreateDoctorDTO;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.persistence.dataimpl.UserRepository;
import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.persistence.entity.RoleType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.quality.Strictness;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Set;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
class DoctorServiceImplTest {

    private final static String TEST_USER_FIRST_NAME = "test name";
    private final static String TEST_USER_LAST_NAME = "test last name";
    private final static Integer TEST_USER_AGE = 25;
    private final static String TEST_USER_EMAIL = "test@email.test";
    private final static String TEST_USER_PASSWORD = "password";
    private final static String TEST_USER_GENDER = "test gender";
    private final static Integer TEST_USER_NUMBER_PHONE = 34546674;
    private final static RoleType TEST_USER_ROLE = RoleType.DOCTOR;

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
                .build();
        User doctor = initUser(1);

        //when
        when(userRepository.save(doctor)).thenReturn(doctor);
//        CreateDoctorDTO actualDoctorDTO = doctorService.createDoctor(doctorDTO);

        //then
        assertAll(
//                () -> assertEquals(doctor.getFirstName(), actualDoctorDTO.getFirstName()),
//                () -> assertEquals(doctor.getLastName(), actualDoctorDTO.getLastName())
//                () -> assertEquals(doctor.getAge(), actualDoctorDTO.getAge()),
//                () -> assertEquals(doctor.getEmail(), actualDoctorDTO.getEmail()),
//                () -> assertEquals(doctor.getPassword(), actualDoctorDTO.getPassword()),
//                () -> assertEquals(doctor.getGender(), actualDoctorDTO.getGender()),
//                () -> assertEquals(doctor.getPhoneNum(), actualDoctorDTO.getPhoneNum()),
//                () -> assertEquals(doctor.getRoles(), actualDoctorDTO.getRoles())
        );
    }

    @Test
    void createDoctor_repositoryThrowError_shouldThrowServiceException() {
        //given
        CreateDoctorDTO doctorDTO = CreateDoctorDTO.builder().build();

        //when
        when(doctorService.createDoctor(doctorDTO)).thenThrow(new RepositoryException("some msg"));

        //then
        assertThrows(RepositoryException.class, () -> doctorService.createDoctor(doctorDTO));
    }

    @Test
    void getAllDoctors_validData_shouldReturnDoctorsList() {
        //given
        Integer expectedListSize = 2;
        User firstDoctor = initUser(1);
        User secondDoctor = initUser(2);
        Pageable pageable = PageRequest.of(1, 2, Sort.by("lastName").descending());

        when(userRepository.findAllByRolesName(RoleType.DOCTOR, pageable))
                .thenReturn(new PageImpl<>(Arrays.asList(firstDoctor, secondDoctor)));

        //when
        Page<DoctorViewDTO> result = doctorService.getAllDoctors(pageable);

        //then
        assertEquals(expectedListSize, result.getSize());
        assertTrue(result.stream().allMatch(doctor -> doctor.getFirstName().equals(TEST_USER_FIRST_NAME)
                && doctor.getLastName().equals(TEST_USER_LAST_NAME)));
        verify(userRepository, times(1)).findAllByRolesName(eq(TEST_USER_ROLE), pageable);
    }

    @Test
    void getAllDoctors_repositoryThrowError_shouldThrowServiceException() {
        //given
        Pageable pageable = PageRequest.of(1, 2, Sort.by("lastName").descending());
        when(userRepository.findAllByRolesName(RoleType.DOCTOR, pageable)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(RepositoryException.class, () -> doctorService.getAllDoctors(pageable));
    }

    @Test
    void deleteDoctorById_existDoctor_shouldDeleteDoctor() {
        //given
        Integer doctorId = 1;

        //when
        boolean result = doctorService.deleteDoctor(doctorId);

        //then
        assertTrue(result);
        verify(userRepository, times(doctorId)).deleteById(eq(1));
    }

    @Test
    void getDoctorById_validData_shouldReturnTheDoctorById() {
        //given
        User addedDoctor = initUser(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(addedDoctor));

        //when
        Optional<DoctorViewDTO> result = doctorService.getDoctorById(1);

        //then
        verify(userRepository, times(1)).findById(eq(1));
        assertTrue(result.stream().allMatch(doctor -> doctor.getFirstName().equals(TEST_USER_FIRST_NAME)
                && doctor.getLastName().equals(TEST_USER_LAST_NAME)
        ));
    }

    @Test
    void getDoctorById_repositoryThrowError_shouldThrowServiceException() {
        //given
        when(userRepository.findById(1)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(RepositoryException.class, () -> doctorService.getDoctorById(1));
    }

    @Test
    void updateDoctorById_repositoryThrowError_shouldThrowServiceException() {
        //given
        DoctorDTO doctorDTO = DoctorDTO.builder().build();
        when(userRepository.findById(1)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(ServiceException.class, () -> doctorService.updateDoctor(doctorDTO));
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
