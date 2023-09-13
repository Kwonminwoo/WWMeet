package com.example.wwmeet_backend.controller;

import com.example.wwmeet_backend.domain.Appointment;
import com.example.wwmeet_backend.dto.AppointmentRequestDto;
import com.example.wwmeet_backend.dto.AppointmentResponseDto;
import com.example.wwmeet_backend.mapper.AppointmentMapper;
import com.example.wwmeet_backend.mapper.AppointmentMapperImpl;
import com.example.wwmeet_backend.service.AppointmentService;
import com.example.wwmeet_backend.service.VoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AppointmentController.class)
class AppointmentControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    AppointmentService appointmentService;
    @SpyBean
    AppointmentMapperImpl appointmentMapper;
    @Test
    void findAppointmentAllListTest() throws Exception{
        List<Appointment> testList = new ArrayList<>();
        testList.add(new Appointment(1L, "test appointment", "두정", "test1", 3, null));

        given(appointmentService.findAllAppointment(anyList()))
                .willReturn(testList);

        mvc.perform(
                get("/api/appointments")
                    .param("appointmentCodeList", "test1")
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$..appointmentCode", "test1").exists());
    }

    @Test
    void findAppointmentById() throws Exception{
        Appointment appointment = new Appointment(1L, "test", "test", "test1", 2, null);
        given(appointmentService.findAppointmentById(any()))
                .willReturn(appointment);

        long findAppointmentId = 1L;
        mvc.perform(
                get("/api/appointment/" + findAppointmentId)

                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.identificationCode", "test3").exists())
                .andDo(print());
    }

    @Test
    void saveAppointment() throws Exception{
        Appointment appointment = new Appointment(1L, "test", "test", "test1", 2, null);
        given(appointmentService.saveAppointment(any()))
                .willReturn(appointment);

        ObjectMapper mapper = new ObjectMapper();
        AppointmentRequestDto requestAppointment = new AppointmentRequestDto(null, "test", "test", "test", 2, null);
        String appointmentJson = mapper.writeValueAsString(requestAppointment);

        mvc.perform(post("/api/appointment")
                        .content(appointmentJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.identificationCode", "test1").exists());
    }
}