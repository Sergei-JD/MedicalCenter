package com.itrex.java.lab.controller.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itrex.java.lab.controller.BaseControllerTest;
import com.itrex.java.lab.dto.CreateTimeslotDTO;
import com.itrex.java.lab.dto.TimeslotDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TimeslotControllerTest extends BaseControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
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
        MvcResult mvcResult = mockMvc.perform(get("/timeslot/{id}", timeslotId)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

    @Test
    void getAllTimeslots_validData_shouldReturnTimeslotsList() throws Exception {
        //given
        CreateTimeslotDTO createTimeslotDTO = CreateTimeslotDTO.builder().build();

        // when
        List<CreateTimeslotDTO> expectedResponseBody = Arrays.asList(createTimeslotDTO,  createTimeslotDTO);
        when(timeslotService.getAllTimeslot()).thenReturn(expectedResponseBody);

        //then
        MvcResult mvcResult = mockMvc.perform(get("/timeslots")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

    @Test
    void deleteTimeslot_validDate_shouldReturnTrue() throws Exception {
        //given
        Integer timeslotId = 1;

        // when
        when(timeslotService.deleteTimeslot(timeslotId)).thenReturn(true);

        //then
        mockMvc.perform(delete("/timeslot/delete/{id}", timeslotId)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteTimeslot_notValidDate_shouldReturnFalse() throws Exception {
        //given
        Integer timeslotId = 1;

        // when
        when(timeslotService.deleteTimeslot(timeslotId)).thenReturn(false);

        //then
        mockMvc.perform(delete("/timeslot/delete/{id}", timeslotId)
                        .contentType("application/json"))
                .andExpect(status().isNotModified());
    }

    @Test
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
        MvcResult mvcResult = mockMvc.perform(post("/timeslot/new")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(expectedResponseBody)))
                .andExpect(status().isOk())
                .andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

    @Test
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
        MvcResult mvcResult = mockMvc.perform(put("/timeslot/update")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(expectedResponseBody)))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
    }

}
