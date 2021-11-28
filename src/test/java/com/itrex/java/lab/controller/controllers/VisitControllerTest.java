package com.itrex.java.lab.controller.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itrex.java.lab.controller.BaseControllerTest;
import com.itrex.java.lab.dto.*;
import com.itrex.java.lab.persistence.entity.Role;
import com.itrex.java.lab.persistence.entity.RoleType;
import com.itrex.java.lab.persistence.entity.Timeslot;
import com.itrex.java.lab.persistence.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VisitControllerTest extends BaseControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getVisitById_validData_shouldReturnVisitById() throws Exception {
        //given
        Integer visitId = 1;
        VisitViewDTO expectedResponseBody = VisitViewDTO.builder()
                .visitId(visitId)
                .doctor(User.builder()
                        .firstName("test first name")
                        .lastName("test last name")
                        .roles(Set.of(Role.builder().name(RoleType.DOCTOR).build())).build())
                .patient(User.builder()
                        .firstName("test first name")
                        .lastName("test last name")
                        .roles(Set.of(Role.builder().name(RoleType.PATIENT).build())).build())
                .timeslot(Timeslot.builder()
                        .startTime(Instant.parse("2021-04-09T15:30:45.123Z"))
                        .date(Instant.parse("2021-04-09T15:30:45.123Z"))
                        .office(505)
                        .build())
                .build();

        // when
        when(visitService.getVisitById(visitId)).thenReturn(Optional.of(expectedResponseBody));
        //then
        MvcResult mvcResult = mockMvc.perform(get("/med/visits/{id}", visitId)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

    @Test
    void getAllVisits_validData_shouldReturnVisitsList() throws Exception {
        //given
        VisitViewDTO visitViewDTO = VisitViewDTO.builder().build();

        // when
        List<VisitViewDTO> expectedResponseBody = Arrays.asList(visitViewDTO,  visitViewDTO);
        when(visitService.getAllVisit()).thenReturn(expectedResponseBody);

        //then
        MvcResult mvcResult = mockMvc.perform(get("/med/visits")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

    @Test
    void getAllFreeVisits_validData_shouldReturnVisitsList() throws Exception {
        //given
        VisitViewDTO visitViewDTO = VisitViewDTO.builder().build();

        // when
        List<VisitViewDTO> expectedResponseBody = Arrays.asList(visitViewDTO,  visitViewDTO);
        when(visitService.getAllFreeVisits()).thenReturn(expectedResponseBody);

        //then
        MvcResult mvcResult = mockMvc.perform(get("/med/visits/free")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

    @Test
    void getAllFreeVisitsForDoctorById_validData_shouldReturnVisitsList() throws Exception {
        //given
        Integer visitId = 1;
        VisitViewDTO visitViewDTO = VisitViewDTO.builder().build();

        // when
        List<VisitViewDTO> expectedResponseBody = Arrays.asList(visitViewDTO,  visitViewDTO);
        when(visitService.getAllFreeVisitsForDoctorById(visitId)).thenReturn(expectedResponseBody);

        //then
        MvcResult mvcResult = mockMvc.perform(get("/med/visits/doctors/free/{id}", 1)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

    @Test
    void getAllVisitsForPatientById_validData_shouldReturnVisitsList() throws Exception {
        //given
        Integer visitId = 1;
        VisitViewDTO visitViewDTO = VisitViewDTO.builder().build();

        // when
        List<VisitViewDTO> expectedResponseBody = Arrays.asList(visitViewDTO,  visitViewDTO);
        when(visitService.getAllVisitsForPatientDyId(visitId)).thenReturn(expectedResponseBody);

        //then
        MvcResult mvcResult = mockMvc.perform(get("/med/visits/patients/{id}", 1)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

    @Test
    void deleteVisit_validDate_shouldReturnTrue() throws Exception {
        //given
        Integer visitId = 1;
        // when
        when(visitService.deleteVisit(visitId)).thenReturn(true);
        //then
        mockMvc.perform(delete("/med/visits/{id}", visitId)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteVisit_notValidDate_shouldReturnFalse() throws Exception {
        //given
        Integer visitId = 1;

        // when
        when(visitService.deleteVisit(visitId)).thenReturn(false);

        //then
        mockMvc.perform(delete("/med/visits/{id}", visitId)
                        .contentType("application/json"))
                .andExpect(status().isNotModified());
    }

    @Test
    void createVisit_validData_shouldReturnNewVisitDTO() throws Exception {
        //given
        CreateVisitDTO expectedResponseBody = CreateVisitDTO.builder()
                .doctorId(User.builder()
                        .firstName("test first name")
                        .lastName("test last name")
                        .age(25)
                        .gender("M")
                        .roles(Set.of(Role.builder().name(RoleType.DOCTOR).build())).build())
                .patientId(User.builder()
                        .firstName("test first name")
                        .lastName("test last name")
                        .age(25)
                        .gender("M")
                        .roles(Set.of(Role.builder().name(RoleType.PATIENT).build())).build())
                .timeslotId(Timeslot.builder()
                        .startTime(Instant.parse("2021-04-09T15:30:45.123Z"))
                        .date(Instant.parse("2021-04-09T15:30:45.123Z"))
                        .office(505)
                        .build())
                .build();

        //when
        when(visitService.createVisit(expectedResponseBody)).thenReturn(expectedResponseBody);

        //then
        MvcResult mvcResult = mockMvc.perform(post("/med/visits/add")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(expectedResponseBody)))
                .andExpect(status().isOk())
                .andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

    @Test
    void updateVisit_validData_shouldReturnUpdatedVisitDTO() throws Exception {
        //given
        Integer visitId = 1;
        VisitDTO expectedResponseBody = VisitDTO.builder()
                .visitId(visitId)
                .doctorId(User.builder()
                        .userId(1)
                        .firstName("test first name")
                        .lastName("test last name")
                        .age(25)
                        .gender("M")
                        .roles(Set.of(Role.builder().name(RoleType.DOCTOR).build())).build())
                .patientId(User.builder()
                        .userId(2)
                        .firstName("test first name")
                        .lastName("test last name")
                        .age(25)
                        .gender("M")
                        .roles(Set.of(Role.builder().name(RoleType.PATIENT).build())).build())
                .timeslotId(Timeslot.builder()
                        .timeslotId(1)
                        .startTime(Instant.parse("2021-04-09T15:30:45.123Z"))
                        .date(Instant.parse("2021-04-09T15:30:45.123Z"))
                        .office(505)
                        .build())
                .build();

        //when
        when(visitService.updateVisit(expectedResponseBody)).thenReturn(expectedResponseBody);

        // then
        MvcResult mvcResult = mockMvc.perform(put("/med/visits/{id}", 1)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(expectedResponseBody)))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

}
