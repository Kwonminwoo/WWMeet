package com.example.wwmeet_backend.controller;

import com.example.wwmeet_backend.domain.Appointment;
import com.example.wwmeet_backend.domain.Participant;
import com.example.wwmeet_backend.domain.PossibleSchedule;
import com.example.wwmeet_backend.domain.Vote;
import com.example.wwmeet_backend.dto.VoteRequestDto;
import com.example.wwmeet_backend.mapper.AppointmentMapperImpl;
import com.example.wwmeet_backend.service.AppointmentService;
import com.example.wwmeet_backend.service.ParticipantService;
import com.example.wwmeet_backend.service.VoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(VoteController.class)
class VoteControllerTest {
    @MockBean
    private VoteService voteService;

    @MockBean
    private AppointmentService appointmentService;

    @SpyBean
    private AppointmentMapperImpl appointmentMapper;

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
        given(voteService.saveVoteSchedule(any()))
                .willReturn(new Vote(1L, foundParticipant, new PossibleSchedule(1L, foundAppointment, LocalDateTime.now(), LocalDateTime.now())));

        List<PossibleSchedule> possibleScheduleList = new ArrayList<>();
        possibleScheduleList.add(new PossibleSchedule(null, foundAppointment, LocalDateTime.now(), LocalDateTime.now()));
        possibleScheduleList.add(new PossibleSchedule(null, foundAppointment, LocalDateTime.of(2023, 10, 1, 10, 30), LocalDateTime.now()));

        VoteRequestDto voteRequest = new VoteRequestDto();
        Vote savedVote = null;
        for (PossibleSchedule possible : possibleScheduleList) {
            savedVote = voteService.saveVoteSchedule(new Vote(null, foundParticipant, possible));
            voteRequest.addPossibleSchedule(savedVote.getPossibleSchedule());
        }
        voteRequest.setAppointmentId(foundAppointment.getId());
        voteRequest.setParticipantId(savedVote.getParticipant().getId());

        ObjectMapper objectMapper = new ObjectMapper();
        String voteRequestJson = objectMapper.writeValueAsString(voteRequest);
        mvc.perform(post("/api/appointment/vote")
                        .content(voteRequestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$..participantId", 1L).exists());
    }
}