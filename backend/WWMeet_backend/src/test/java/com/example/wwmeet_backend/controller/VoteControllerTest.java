package com.example.wwmeet_backend.controller;

import com.example.wwmeet_backend.domain.Appointment;
import com.example.wwmeet_backend.domain.Participant;
import com.example.wwmeet_backend.domain.PossibleSchedule;
import com.example.wwmeet_backend.domain.Vote;
import com.example.wwmeet_backend.service.AppointmentService;
import com.example.wwmeet_backend.service.ParticipantService;
import com.example.wwmeet_backend.service.VoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
class VoteControllerTest {
    @MockBean
    private VoteService voteService;

    @MockBean
    private AppointmentService appointmentService;

    @MockBean
    private ParticipantService participantService;

    @Autowired
    MockMvc mvc;

    @Test
    void voteAppointmentSchedule() throws Exception{
        Appointment foundAppointment = new Appointment(1L, "tesT", "test", "test1", 2, null);
        given(appointmentService.findAppointmentById(any()))
                .willReturn(foundAppointment);
        Participant foundParticipant = new Participant(1L, foundAppointment, "testA");
        given(participantService.findParticipantById(any()))
                .willReturn(foundParticipant);
        given(voteService.saveVote(any()))
                .willReturn(new Vote(1L, foundParticipant, null));

        List<PossibleSchedule> possibleScheduleList = new ArrayList<>();
        possibleScheduleList.add(new PossibleSchedule(null, foundAppointment, LocalDateTime.now(), null));
        possibleScheduleList.add(new PossibleSchedule(null, foundAppointment, LocalDateTime.of(2023, 10, 1, 10, 30), null));

        List<Vote> voteList = new ArrayList<>();
        for (PossibleSchedule possible : possibleScheduleList) {
            Vote vote = voteService.saveVote(Vote(null, foundParticipant, possible));
            voteList.add(vote);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String voteListJson = objectMapper.writeValueAsString(voteList);
        mvc.perform(post("api/appointment/vote")
                        .content(voteListJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.participant.id", 1L).exists());
    }
}