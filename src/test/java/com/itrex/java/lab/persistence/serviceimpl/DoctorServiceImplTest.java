package com.itrex.java.lab.persistence.serviceimpl;

import com.itrex.java.lab.converter.UserConverter;
import com.itrex.java.lab.dto.DoctorViewDTO;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.persistence.entity.User;
import com.itrex.java.lab.persistence.repository.RoleRepository;
import com.itrex.java.lab.persistence.repository.UserRepository;
import com.itrex.java.lab.service.impl.DoctorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceImplTest {

    @InjectMocks
    private DoctorServiceImpl doctorService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserConverter userConverter;

    @Test
    void getAllDoctors_validData_shouldReturnDoctorsList() {
        //given
        Integer expectedListSize = 2;
        User doctor = User.builder().build();
        DoctorViewDTO doctorViewDTO = DoctorViewDTO.builder().build();

        //when
        when(userConverter.toDoctorViewDTO(doctor)).thenReturn(doctorViewDTO);
        when(userRepository.getAllUsersByRole(RoleType.DOCTOR))
                .thenReturn(Arrays.asList(doctor, doctor));
        List<DoctorViewDTO> actualList = doctorService.getAllDoctors();

        //then
        assertEquals(expectedListSize, actualList.size());
    }

    @Test
    void getDoctorById_invalidData_shouldReturnEmptyOptional() {
        //given
        Integer doctorId = -1;

        //when
        when(userRepository.getUserById(doctorId)).thenReturn(Optional.empty());
        Optional<DoctorViewDTO> actualOptDoctor = doctorService.getDoctorById(doctorId);

        // then
        assertTrue(actualOptDoctor.isEmpty());
    }


    @Test
    void deleteDoctorById_existDoctor_shouldDeleteDoctor() {
        //given
        Integer doctorId = 1;

        //when
        when(userRepository.deleteUserById(doctorId)).thenReturn(true);

        //then
        assertTrue(doctorService.deleteDoctor(doctorId));
    }

    @Test
    void deleteDoctorById_invalidData_shouldNotDeleteDoctor() {
        //given
        Integer doctorId = -1;
        //when
        when(userRepository.deleteUserById(doctorId)).thenReturn(false);

        //then
        assertFalse(doctorService.deleteDoctor(doctorId));
    }

}
