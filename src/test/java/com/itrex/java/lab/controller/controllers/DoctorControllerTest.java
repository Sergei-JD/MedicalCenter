package com.itrex.java.lab.controller.controllers;

import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import com.itrex.java.lab.dto.DoctorDTO;
import com.itrex.java.lab.dto.DoctorViewDTO;
import com.itrex.java.lab.dto.CreateDoctorDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.itrex.java.lab.controller.BaseControllerTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

class DoctorControllerTest extends BaseControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = {"admin"})
    void createDoctor_validData_shouldReturnNewDoctorDTO() throws Exception {
        //given
        CreateDoctorDTO expectedResponseBody = CreateDoctorDTO.builder()
                .firstName("test first name")
                .lastName("test lsat name")
                .age(25)
                .gender("M")
//                .roles(Set.of(Role.builder().name(RoleType.DOCTOR).build()))
                .build();

        //when
        when(doctorService.createDoctor(expectedResponseBody)).thenReturn(expectedResponseBody);

        //then
        MvcResult mvcResult = mockMvc.perform(post("/med/doctors/add")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(expectedResponseBody)))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"admin"})
    void deleteDoctor_validDate_shouldReturnTrue() throws Exception {
        //given
        Integer doctorId = 1;
        // when
        when(doctorService.deleteDoctor(doctorId)).thenReturn(true);
        //then
        mockMvc.perform(delete("/med/doctors/{id}", doctorId)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"admin"})
    void deleteDoctor_notValidDate_shouldReturnFalse() throws Exception {
        //given
        Integer doctorId = 1;

        // when
        when(doctorService.deleteDoctor(doctorId)).thenReturn(false);

        //then
        mockMvc.perform(delete("/med/doctors/{id}", doctorId)
                        .contentType("application/json"))
                .andExpect(status().isNotModified());
    }

    @Test
    @WithMockUser(username = "unnecessary", roles = {"admin", "doctor", "patient"})
    void getAllDoctors_validData_shouldReturnDoctorsList() throws Exception {
        //given
        DoctorViewDTO firstDoctorViewDTO = DoctorViewDTO.builder().build();
        DoctorViewDTO secondDoctorViewDTO = DoctorViewDTO.builder().build();
        Pageable pageable = PageRequest.of(1, 2, Sort.by("lastName").descending());

        // when
        Page<DoctorViewDTO> expectedResponseBody =
                new PageImpl<>(Arrays.asList(firstDoctorViewDTO, secondDoctorViewDTO));
        when(doctorService.getAllDoctors(pageable)).thenReturn(expectedResponseBody);

        //then
        MvcResult mvcResult = mockMvc.perform(get("/med/doctors")
                        .contentType("application/json")
                        .param("page", "1")
                        .param("size", "2")
                        .param("sort", "lastName, desc"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

    @Test
    @WithMockUser(username = "unnecessary", roles = {"admin", "doctor", "patient"})
    void getDoctorById_validData_shouldReturnDoctorById() throws Exception {
        //given
        Integer doctorId = 1;
        DoctorViewDTO expectedResponseBody = DoctorViewDTO.builder()
                .firstName("test first name")
                .lastName("test lsat name")
                .build();

        // when
        when(doctorService.getDoctorById(doctorId)).thenReturn(Optional.of(expectedResponseBody));
        //then
        MvcResult mvcResult = mockMvc.perform(get("/med/doctors/{id}", doctorId)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

    @Test
    @WithMockUser(username = "unnecessary", roles = {"admin", "doctor"})
    void getDoctorByEmail_validData_shouldReturnDoctorByEmail() throws Exception {
        //given
        String doctorEmail = "some@email";
        DoctorDTO expectedResponseBody = DoctorDTO.builder()
                .firstName("test first name")
                .lastName("test lsat name")
                .age(35)
                .email(doctorEmail)
                .gender("M")
                .build();

        // when
        when(doctorService.getDoctorByEmail(doctorEmail)).thenReturn(Optional.of(expectedResponseBody));
        //then
        MvcResult mvcResult = mockMvc.perform(get("/med/doctors/email", doctorEmail)
                        .contentType("application/json")
                        .param("email", doctorEmail))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"admin"})
    void updateDoctor_validData_shouldReturnUpdatedDoctorDTO() throws Exception {
        //given
        Integer doctorId = 1;
        DoctorDTO expectedResponseBody = DoctorDTO.builder()
                .userId(doctorId)
                .firstName("test first name")
                .lastName("test lsat name")
                .age(25)
                .gender("M")
//                .roles(Set.of(Role.builder().name(RoleType.DOCTOR).build()))
                .build();

        //when
        when(doctorService.updateDoctor(expectedResponseBody)).thenReturn(expectedResponseBody);

        // then
        MvcResult mvcResult = mockMvc.perform(put("/med/doctors", 1)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(expectedResponseBody)))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

}
