package com.itrex.java.lab.controller.controllers;

import java.util.Arrays;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import com.itrex.java.lab.dto.TimeslotDTO;
import com.itrex.java.lab.dto.CreateTimeslotDTO;
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

class TimeslotControllerTest extends BaseControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", roles = {"admin", "doctor"})
    void createTimeslot_validData_shouldReturnNewTimeslotDTO() throws Exception {
        //given
        CreateTimeslotDTO expectedResponseBody = CreateTimeslotDTO.builder()
                .startTime(Instant.parse("2021-04-09T15:30:45.123Z"))
                .date(Instant.parse("2021-04-09T15:30:45.123Z"))
                .office(505)
                .build();

        //when
        when(timeslotService.createTimeslot(expectedResponseBody)).thenReturn(expectedResponseBody);

        //then
        MvcResult mvcResult = mockMvc.perform(post("/med/timeslots")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(expectedResponseBody)))
                .andExpect(status().isCreated())
                .andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

    @Test
    @WithMockUser(username = "user", roles = {"admin", "doctor"})
    void deleteTimeslot_validDate_shouldReturnTrue() throws Exception {
        //given
        Integer timeslotId = 1;

        // when
        when(timeslotService.deleteTimeslot(timeslotId)).thenReturn(true);

        //then
        mockMvc.perform(delete("/med/timeslots/{id}", timeslotId)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"admin", "doctor"})
    void deleteTimeslot_notValidDate_shouldReturnFalse() throws Exception {
        //given
        Integer timeslotId = 1;

        // when
        when(timeslotService.deleteTimeslot(timeslotId)).thenReturn(false);

        //then
        mockMvc.perform(delete("/med/timeslots/{id}", timeslotId)
                        .contentType("application/json"))
                .andExpect(status().isNotModified());
    }

    @Test
    @WithMockUser(username = "user", roles = {"admin", "doctor", "patient"})
    void getAllTimeslots_validData_shouldReturnTimeslotsList() throws Exception {
        //given
        CreateTimeslotDTO firstTimeslotDTO = CreateTimeslotDTO.builder()
                .startTime(Instant.now())
                .date(Instant.now())
                .office(1).build();
        CreateTimeslotDTO secondTimeslotDTO = CreateTimeslotDTO.builder()
                .startTime(Instant.now())
                .date(Instant.now())
                .office(2).build();
        Pageable pageable = PageRequest.of(1, 2, Sort.by("office").descending());

        // when
        Page<CreateTimeslotDTO> expectedResponseBody =
                new PageImpl<>(Arrays.asList(firstTimeslotDTO,  secondTimeslotDTO));
        when(timeslotService.getAllTimeslot(pageable)).thenReturn(expectedResponseBody);

        //then
        MvcResult mvcResult = mockMvc.perform(get("/med/timeslots")
                        .contentType("application/json")
                        .param("page", "1")
                        .param("size", "2")
                        .param("sort", "office,desc"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

    @Test
    @WithMockUser(username = "user", roles = {"admin", "doctor", "patient"})
    void getTimeslotById_validData_shouldReturnTimeslotById() throws Exception {
        //given
        Integer timeslotId = 1;
        CreateTimeslotDTO expectedResponseBody = CreateTimeslotDTO.builder()
                .startTime(Instant.parse("2021-04-09T15:30:45.123Z"))
                .date(Instant.parse("2021-04-09T15:30:45.123Z"))
                .office(505)
                .build();

        // when
        when(timeslotService.getTimeslotById(timeslotId)).thenReturn(Optional.of(expectedResponseBody));
        //then
        MvcResult mvcResult = mockMvc.perform(get("/med/timeslots/{id}", timeslotId)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

    @Test
    @WithMockUser(username = "user", roles = {"admin", "doctor"})
    void updateTimeslot_validData_shouldReturnUpdatedTimeslotDTO() throws Exception {
        //given
        Integer timeslotId = 1;
        TimeslotDTO expectedResponseBody = TimeslotDTO.builder()
                .timeslotId(timeslotId)
                .startTime(Instant.parse("2021-04-09T15:30:45.123Z"))
                .date(Instant.parse("2021-04-09T15:30:45.123Z"))
                .office(505)
                .build();

        //when
        when(timeslotService.updateTimeslot(expectedResponseBody)).thenReturn(expectedResponseBody);

        // then
        MvcResult mvcResult = mockMvc.perform(put("/med/timeslots", 1)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(expectedResponseBody)))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

}
