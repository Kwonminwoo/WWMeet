package com.example.wwmeet_backend.controller;

import com.example.wwmeet_backend.domain.Appointment;
import com.example.wwmeet_backend.dto.AppointmentFindDto;
import com.example.wwmeet_backend.mapper.AppointmentMapper;
import com.example.wwmeet_backend.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class AppointmentControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    AppointmentService appointmentService; // 가짜 객체를 만들어 활용, Controller에서 Service를 의존하므로 사용

    @MockBean
    AppointmentMapper appointmentMapper;

    @Test
    void findAppointmentAllListTest() throws Exception{
        List<Appointment> testList = new ArrayList<>();
        testList.add(new Appointment(1L, "test appointment", "두정", "test1", 3, null, null));

        given(appointmentMapper.toFindDto(any()))
                .willReturn(new AppointmentFindDto(1L, "test appointment", "두정", "test1", 3, null));

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
        Appointment appointment = new Appointment(1L, "test", "test", "test1", 2, null, null);

        given(appointmentService.findAppointmentById(1L))
                .willReturn(appointment);
        given(appointmentMapper.toFindDto(any()))
                .willReturn(new AppointmentFindDto(1L, "test", "test", "test", 2, null));

        long findAppointmentId = 1L;
        mvc.perform(
                get("/api/appointment/" + findAppointmentId)

                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appointmentCode", "test1").exists())
                .andDo(print());
    }
}