package com.itrex.java.lab.controller.controllers;

import java.util.List;
import java.util.Arrays;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import com.itrex.java.lab.dto.VisitDTO;
import com.itrex.java.lab.dto.TimeslotDTO;
import com.itrex.java.lab.dto.VisitViewDTO;
import com.itrex.java.lab.dto.DoctorViewDTO;
import com.itrex.java.lab.dto.PatientViewDTO;
import com.itrex.java.lab.dto.CreateVisitDTO;
import com.itrex.java.lab.persistence.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.itrex.java.lab.persistence.entity.Timeslot;
import com.itrex.java.lab.controller.BaseControllerTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

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
                .doctor(DoctorViewDTO.builder()
                        .firstName("test first name")
                        .lastName("test last name")
                        .build())
                .patient(PatientViewDTO.builder()
                        .firstName("test first name")
                        .lastName("test last name")
                        .build())
                .timeslot(TimeslotDTO.builder()
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
        VisitDTO expectedResponseBody = VisitDTO.builder()
                .doctorId(User.builder().userId(1).build().getUserId())
                .patientId(User.builder().userId(2).build().getUserId())
                .timeslotId(Timeslot.builder().timeslotId(1).build().getTimeslotId())
                .build();

        CreateVisitDTO requestBody = CreateVisitDTO.builder()
                .doctorId(User.builder().userId(1).build().getUserId())
                .patientId(User.builder().userId(2).build().getUserId())
                .timeslotId(Timeslot.builder().timeslotId(1).build().getTimeslotId())
                .build();

        //when
        when(visitService.createVisit(requestBody)).thenReturn(expectedResponseBody);

        //then
        MvcResult mvcResult = mockMvc.perform(post("/med/visits/add")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestBody)))
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
                .doctorId(1)
                .patientId(1)
                .timeslotId(1)
                .build();

        //when
        when(visitService.updateVisit(expectedResponseBody)).thenReturn(expectedResponseBody);

        // then
        MvcResult mvcResult = mockMvc.perform(put("/med/visits", 1)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(expectedResponseBody)))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

}
