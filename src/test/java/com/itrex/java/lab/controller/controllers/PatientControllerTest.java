package com.itrex.java.lab.controller.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itrex.java.lab.controller.BaseControllerTest;
import com.itrex.java.lab.dto.CreatePatientDTO;
import com.itrex.java.lab.dto.PatientDTO;
import com.itrex.java.lab.dto.PatientViewDTO;
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


class PatientControllerTest extends BaseControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPatientById_validData_shouldReturnPatientById() throws Exception {
        //given
        Integer patientId = 1;
        PatientViewDTO expectedResponseBody = PatientViewDTO.builder()
                .firstName("test first name")
                .lastName("test lsat name")
                .roles(Set.of(Role.builder().name(RoleType.PATIENT).build()))
                .build();

        // when
        when(patientService.getPatientById(patientId)).thenReturn(Optional.of(expectedResponseBody));
        //then
        MvcResult mvcResult = mockMvc.perform(get("/patients/{id}", patientId)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

    @Test
    void getAllPatients_validData_shouldReturnPatientsList() throws Exception {
        //given
        PatientViewDTO patientViewDTO = PatientViewDTO.builder().build();

        // when
        List<PatientViewDTO> expectedResponseBody = Arrays.asList(patientViewDTO,  patientViewDTO);
        when(patientService.getAllPatients()).thenReturn(expectedResponseBody);

        //then
        MvcResult mvcResult = mockMvc.perform(get("/patients")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

    @Test
    void deletePatient_validDate_shouldReturnTrue() throws Exception {
        //given
        Integer patientId = 1;

        // when
        when(patientService.deletePatient(patientId)).thenReturn(true);

        //then
        mockMvc.perform(delete("/patients/{id}", patientId)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void deletePatient_notValidDate_shouldReturnFalse() throws Exception {
        //given
        Integer patientId = 1;

        // when
        when(patientService.deletePatient(patientId)).thenReturn(false);

        //then
        mockMvc.perform(delete("/patients/{id}", patientId)
                        .contentType("application/json"))
                .andExpect(status().isNotModified());
    }

    @Test
    void createPatient_validData_shouldReturnNewPatientDTO() throws Exception {
        //given
        CreatePatientDTO expectedResponseBody = CreatePatientDTO.builder()
                .firstName("test first name")
                .lastName("test lsat name")
                .age(25)
                .gender("M")
                .roles(Set.of(Role.builder().name(RoleType.PATIENT).build()))
                .build();

        //when
        when(patientService.createPatient(expectedResponseBody)).thenReturn(expectedResponseBody);

        //then
        MvcResult mvcResult = mockMvc.perform(post("/patients/new")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(expectedResponseBody)))
                .andExpect(status().isOk())
                .andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

    @Test
    void updatePatient_validData_shouldReturnUpdatedPatientDTO() throws Exception {
        //given
        Integer patientId = 1;
        PatientDTO expectedResponseBody = PatientDTO.builder()
                .userId(patientId)
                .firstName("test first name")
                .lastName("test lsat name")
                .age(25)
                .gender("M")
                .roles(Set.of(Role.builder().name(RoleType.PATIENT).build()))
                .build();

        //when
        when(patientService.updatePatient(expectedResponseBody)).thenReturn(expectedResponseBody);

        // then
        MvcResult mvcResult = mockMvc.perform(put("/patients/{id}", 1)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(expectedResponseBody)))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

}
