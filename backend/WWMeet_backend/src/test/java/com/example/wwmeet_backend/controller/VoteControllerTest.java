package com.example.wwmeet_backend.controller;

import com.example.wwmeet_backend.domain.Appointment;
import com.example.wwmeet_backend.domain.Vote;
import com.example.wwmeet_backend.dto.VoteRequestDto;
import com.example.wwmeet_backend.dto.VoteResponseDto;
import com.example.wwmeet_backend.service.AppointmentService;
import com.example.wwmeet_backend.service.VoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Request;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultHandlersDsl;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
class VoteControllerTest {
    @MockBean
    private VoteService voteService;
    @MockBean
    private AppointmentService appointmentService;
    @Autowired
    MockMvc mvc;

    @Test
    void voteAppointmentSchedule() throws Exception{
        Map<LocalDateTime, LocalDateTime> scheduleMap = new HashMap<>();
        LocalDateTime startSchedule = LocalDateTime.of(2023, 9, 11, 9, 0);
        LocalDateTime endSchedule = LocalDateTime.of(2023, 9, 11, 10, 0);
        scheduleMap.put(startSchedule, endSchedule);
        VoteRequestDto voteRequestDto = new VoteRequestDto(1L, scheduleMap);
        Appointment foundAppointment = new Appointment(1L, "test", "test", "test1", 2, null, null);

        given(appointmentService.findAppointmentById(voteRequestDto.getAppointmentId()))
                .willReturn(foundAppointment);
        given(voteService.voteAppointmentSchedule(foundAppointment, scheduleMap))
                .willReturn(new VoteResponseDto(1L, scheduleMap));

        ObjectMapper objectMapper = new ObjectMapper();
        String voteRequestDtoJson = objectMapper.writeValueAsString(voteRequestDto);
        mvc.perform(
                post("/api/appointment/vote")
                .content(voteRequestDtoJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.appointment_id", 1L).exists());
    }
}