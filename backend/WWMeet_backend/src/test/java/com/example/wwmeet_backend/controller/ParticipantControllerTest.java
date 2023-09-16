package com.example.wwmeet_backend.controller;

import com.example.wwmeet_backend.domain.Appointment;
import com.example.wwmeet_backend.domain.Participant;
import com.example.wwmeet_backend.dto.ParticipantAndAppointmentDto;
import com.example.wwmeet_backend.mapper.AppointmentMapperImpl;
import com.example.wwmeet_backend.service.AppointmentService;
import com.example.wwmeet_backend.service.ParticipantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ParticipantControllerTest.class)
class ParticipantControllerTest {
    @MockBean
    ParticipantService participantService;
    @MockBean
    AppointmentService appointmentService;
    @SpyBean
    AppointmentMapperImpl appointmentMapper;
    @Autowired
    MockMvc mvc;

    @Test
    void saveParticipantOfAppoint() throws Exception{
        Appointment appointment = new Appointment(1L, "test", "test", "test", 2, null);
        Participant participant = new Participant(1L, appointment, "testName");
        given(appointmentService.findAppointmentById(any()))
                .willReturn(appointment);
        given(participantService.addParticipantOfAppointment(any()))
                .willReturn(participant);
        ObjectMapper objectMapper = new ObjectMapper();
        String participantAppointmentRequest = objectMapper.writeValueAsString(new ParticipantAndAppointmentDto("test", "testName"));

        mvc.perform(
                post("api/appointment/participant")
                .content(participantAppointmentRequest)
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.participantName", "testName").exists());
    }
}