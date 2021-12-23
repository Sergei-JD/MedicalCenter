package com.itrex.java.lab.service.dataimpl;

import com.itrex.java.lab.util.UserConversionUtils;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.mockito.quality.Strictness;
import com.itrex.java.lab.dto.DoctorDTO;
import com.itrex.java.lab.dto.DoctorViewDTO;
import com.itrex.java.lab.dto.CreateDoctorDTO;
import org.mockito.junit.jupiter.MockitoSettings;
import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.User;
import org.junit.jupiter.api.extension.ExtendWith;
import com.itrex.java.lab.exception.ServiceException;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.exception.RepositoryException;
import com.itrex.java.lab.persistence.dataimpl.RoleRepository;
import com.itrex.java.lab.persistence.dataimpl.UserRepository;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.Set;
import java.util.Arrays;
import java.util.Optional;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.eq;
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

    @Mock
    private RoleRepository roleRepository;

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
    void getDoctorByEmail_validData_shouldReturnTheDoctorByEmail() {
        //given
        User addedDoctor = initUser(1);

        when(userRepository.findUserByEmail("test@email.test")).thenReturn(Optional.of(addedDoctor));

        //when
        Optional<DoctorDTO> result = doctorService.getDoctorByEmail("test@email.test");

        //then
        verify(userRepository, times(1)).findUserByEmail(eq("test@email.test"));
        assertTrue(result.stream().allMatch(doctor -> doctor.getFirstName().equals(TEST_USER_FIRST_NAME)
                && doctor.getLastName().equals(TEST_USER_LAST_NAME)
                && doctor.getEmail().equals(TEST_USER_EMAIL)
        ));
    }

    @Test
    void getDoctorByEmail_repositoryThrowError_shouldThrowServiceException() {
        //given
        when(userRepository.findUserByEmail("some@email")).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(RepositoryException.class, () -> doctorService.getDoctorByEmail("some@email"));
    }

    @Test
    void createDoctor_validData_shouldCreateDoctor() {
        //given
        CreateDoctorDTO doctorDTO = CreateDoctorDTO.builder()
                .firstName(TEST_USER_FIRST_NAME)
                .lastName(TEST_USER_LAST_NAME)
                .age(TEST_USER_AGE)
                .email(TEST_USER_EMAIL)
                .password(TEST_USER_PASSWORD)
                .gender(TEST_USER_GENDER)
                .phoneNum(TEST_USER_NUMBER_PHONE)
                .build();

        Role roleDoctor = Role.builder()
                .name(RoleType.DOCTOR)
                .build();

        //when
        when(roleRepository.findRoleByName(eq(RoleType.DOCTOR))).thenReturn(Optional.of(roleDoctor));
        CreateDoctorDTO actualDoctorDTO = doctorService.createDoctor(doctorDTO);

        Optional<DoctorDTO> result = doctorService.getDoctorByEmail(doctorDTO.getEmail());

        //then
        verify(userRepository, times(1)).findUserByEmail(eq(doctorDTO.getEmail()));

        assertTrue(result.stream().allMatch(doctor -> doctor.getFirstName().equals(actualDoctorDTO.getFirstName())
                && doctor.getLastName().equals(actualDoctorDTO.getLastName())
                && doctor.getAge().equals(actualDoctorDTO.getAge())
                && doctor.getEmail().equals(actualDoctorDTO.getEmail())
                && doctor.getPassword().equals(actualDoctorDTO.getPassword())
                && doctor.getGender().equals(actualDoctorDTO.getGender())
        ));
    }

//    @Test
//    void updateDoctorById_validData_shouldUpdateDoctor() {
//        //given
//        Integer doctorId = 1;
//        String doctorFirstName = "otherName";
//
//        CreateDoctorDTO doctorDTO = CreateDoctorDTO.builder()
//                .firstName(TEST_USER_FIRST_NAME)
//                .lastName(TEST_USER_LAST_NAME)
//                .age(TEST_USER_AGE)
//                .email(TEST_USER_EMAIL)
//                .password(TEST_USER_PASSWORD)
//                .gender(TEST_USER_GENDER)
//                .phoneNum(TEST_USER_NUMBER_PHONE)
//                .build();
//
//        Role roleDoctor = Role.builder()
//                .name(RoleType.DOCTOR)
//                .build();
//
//        User user = initUser()
//
//        when(roleRepository.findRoleByName(eq(RoleType.DOCTOR))).thenReturn(Optional.of(roleDoctor));
//        CreateDoctorDTO actualDoctorDTO = doctorService.createDoctor(doctorDTO);
//
//        when(doctorService.createDoctor(doctorDTO)).thenReturn(doctorDTO);
//
//        //when
//        when(userRepository.findById(doctorId)).thenReturn(Optional.of())
//
//
////
////        when(roleRepository.findRoleByName(eq(RoleType.DOCTOR))).thenReturn(Optional.of(roleDoctor));
////        CreateDoctorDTO actualDoctorDTO = doctorService.createDoctor(doctorDTO);
//
//
//        //when && then
//    }

    @Test
    void updateDoctorById_repositoryThrowError_shouldThrowServiceException() {
        //given
        DoctorDTO doctorDTO = DoctorDTO.builder().build();
        when(userRepository.findById(1)).thenThrow(new RepositoryException("some msg"));

        //when && then
        assertThrows(ServiceException.class, () -> doctorService.updateDoctor(doctorDTO));
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
