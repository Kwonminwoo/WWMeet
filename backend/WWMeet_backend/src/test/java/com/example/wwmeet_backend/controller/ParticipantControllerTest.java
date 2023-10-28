package com.example.wwmeet_backend.controller;

import com.example.wwmeet_backend.appointment.service.AppointmentService;
import com.example.wwmeet_backend.participant.controller.ParticipantController;
import com.example.wwmeet_backend.participant.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(ParticipantController.class)
class ParticipantControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    ParticipantService participantService;
    @MockBean
    AppointmentService appointmentService;

//    @Test
//    void saveParticipantOfAppoint() throws Exception{
//        Appointment appointment = new Appointment(1L, "test", "test", "test", 2, null);
//        Participant participant = new Participant(1L, appointment, "testName");
//        given(appointmentService.findAppointmentById(any()))
//                .willReturn(appointment);
//        given(participantService.addParticipantOfAppointment(any()))
//                .willReturn(participant);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String participantAppointmentRequest = objectMapper.writeValueAsString(new ParticipantAndAppointmentDto("test", "testName"));
//
//        mvc.perform(
//                post("/api/appointment/participant")
//                .content(participantAppointmentRequest)
//                .contentType(MediaType.APPLICATION_JSON)
//        )
//        .andExpect(status().isOk())
//        .andDo(print())
//        .andExpect(jsonPath("$.participantName", "testName").exists());
//    }
}