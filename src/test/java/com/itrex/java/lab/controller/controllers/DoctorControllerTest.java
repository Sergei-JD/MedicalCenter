package com.itrex.java.lab.controller.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itrex.java.lab.controller.BaseControllerTest;
import com.itrex.java.lab.dto.CreateDoctorDTO;
import com.itrex.java.lab.dto.DoctorDTO;
import com.itrex.java.lab.dto.DoctorViewDTO;
import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.RoleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DoctorControllerTest extends BaseControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getDoctorById_validData_shouldReturnDoctorById() throws Exception {
        //given
        Integer doctorId = 1;
        DoctorViewDTO expectedResponseBody = DoctorViewDTO.builder()
                .firstName("test first name")
                .lastName("test lsat name")
                .roles(Set.of(Role.builder().name(RoleType.DOCTOR).build()))
                .build();

        // when
        when(doctorService.getDoctorById(doctorId)).thenReturn(Optional.of(expectedResponseBody));
        //then
        MvcResult mvcResult = mockMvc.perform(get("/doctor/{id}", doctorId)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

    @Test
    void getAllDoctors_validData_shouldReturnDoctorsList() throws Exception {
        //given
        DoctorViewDTO doctorViewDTO = DoctorViewDTO.builder().build();

        // when
        List<DoctorViewDTO> expectedResponseBody = Arrays.asList(doctorViewDTO, doctorViewDTO);
        when(doctorService.getAllDoctors()).thenReturn(expectedResponseBody);

        //then
        MvcResult mvcResult = mockMvc.perform(get("/doctors")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

    @Test
    void deleteDoctor_validDate_shouldReturnTrue() throws Exception {
        //given
        Integer doctorId = 1;
        // when
        when(doctorService.deleteDoctor(doctorId)).thenReturn(true);
        //then
        mockMvc.perform(delete("/doctor/delete/{id}", doctorId)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteDoctor_notValidDate_shouldReturnFalse() throws Exception {
        //given
        Integer doctorId = 1;

        // when
        when(doctorService.deleteDoctor(doctorId)).thenReturn(false);

        //then
        mockMvc.perform(delete("/doctor/delete/{id}", doctorId)
                        .contentType("application/json"))
                .andExpect(status().isNotModified());
    }

//    @Test
//    void createDoctor_validData_shouldReturnNewDoctorDTO() throws Exception {
//        //given
//        CreateDoctorDTO expectedResponseBody = CreateDoctorDTO.builder()
//                .firstName("test first name")
//                .lastName("test lsat name")
//                .age(25)
//                .gender("M")
//                .roles(Set.of(Role.builder().name(RoleType.DOCTOR).build()))
//                .build();
//
//        //when
//        when(doctorService.createDoctor(expectedResponseBody)).thenReturn(expectedResponseBody);
//
//        //then
//        MvcResult mvcResult = mockMvc.perform(post("/doctor/new")
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(expectedResponseBody)))
//                .andExpect(status().isOk())
//                .andReturn();
//        String actualResponseBody = mvcResult.getResponse().getContentAsString();
//
//        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
//    }
//
//    @Test
//    void updateDoctor_validData_shouldReturnUpdatedDoctorDTO() throws Exception {
//        //given
//        Integer doctorId = 1;
//        DoctorDTO expectedResponseBody = DoctorDTO.builder()
//                .userId(doctorId)
//                .firstName("test first name")
//                .lastName("test lsat name")
//                .age(25)
//                .gender("M")
//                .roles(Set.of(Role.builder().name(RoleType.DOCTOR).build()))
//                .build();
//
//        //when
//        when(doctorService.updateDoctor(expectedResponseBody)).thenReturn(expectedResponseBody);
//
//        // then
//        MvcResult mvcResult = mockMvc.perform(put("/doctor/update")
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(expectedResponseBody)))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String actualResponseBody = mvcResult.getResponse().getContentAsString();
//
//        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
//    }

}
