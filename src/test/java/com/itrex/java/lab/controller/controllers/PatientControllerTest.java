package com.itrex.java.lab.controller.controllers;

import java.util.Set;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import com.itrex.java.lab.dto.PatientDTO;
import com.itrex.java.lab.dto.PatientViewDTO;
import com.itrex.java.lab.dto.CreatePatientDTO;
import com.itrex.java.lab.persistence.entity.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import com.itrex.java.lab.persistence.entity.RoleType;
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
                .build();

        // when
        when(patientService.getPatientById(patientId)).thenReturn(Optional.of(expectedResponseBody));
        //then
        MvcResult mvcResult = mockMvc.perform(get("/med/patients/{id}", patientId)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

    @Test
    void getAllPatients_validData_shouldReturnPatientsList() throws Exception {
        //given
        PatientViewDTO firstPatientViewDTO = PatientViewDTO.builder().build();
        PatientViewDTO secondPatientViewDTO = PatientViewDTO.builder().build();
        Pageable pageable = PageRequest.of(1, 2, Sort.by("lastName").descending());

        // when
        Page<PatientViewDTO> expectedResponseBody =
                new PageImpl<>(Arrays.asList(firstPatientViewDTO, secondPatientViewDTO));
        when(patientService.getAllPatients(pageable)).thenReturn(expectedResponseBody);

        //then
        MvcResult mvcResult = mockMvc.perform(get("/med/patients")
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
    void deletePatient_validDate_shouldReturnTrue() throws Exception {
        //given
        Integer patientId = 1;

        // when
        when(patientService.deletePatient(patientId)).thenReturn(true);

        //then
        mockMvc.perform(delete("/med/patients/{id}", patientId)
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
        mockMvc.perform(delete("/med/patients/{id}", patientId)
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
//                .roles(Set.of(Role.builder().name(RoleType.PATIENT).build()))
                .build();

        //when
        when(patientService.createPatient(expectedResponseBody)).thenReturn(expectedResponseBody);

        //then
        MvcResult mvcResult = mockMvc.perform(post("/med/patients/add")
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
//                .roles(Set.of(Role.builder().name(RoleType.PATIENT).build()))
                .build();

        //when
        when(patientService.updatePatient(expectedResponseBody)).thenReturn(expectedResponseBody);

        // then
        MvcResult mvcResult = mockMvc.perform(put("/med/patients", 1)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(expectedResponseBody)))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

}
