package com.example.wwmeet_backend.vote.controller;


import com.example.wwmeet_backend.appointment.domain.Appointment;
import com.example.wwmeet_backend.appointment.service.AppointmentService;
import com.example.wwmeet_backend.participant.domain.Participant;
import com.example.wwmeet_backend.participant.service.ParticipantService;
import com.example.wwmeet_backend.possibleschedule.domain.PossibleSchedule;
import com.example.wwmeet_backend.possibleschedule.dto.PossibleScheduleRequestDto;
import com.example.wwmeet_backend.possibleschedule.dto.PossibleScheduleResponseDto;
import com.example.wwmeet_backend.vote.domain.Vote;
import com.example.wwmeet_backend.vote.dto.VoteRequestDto;
import com.example.wwmeet_backend.vote.dto.VoteResponseDto;
import com.example.wwmeet_backend.vote.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteService;
    private final AppointmentService appointmentService;
    private final ParticipantService participantService;

    @PostMapping("/api/appointment/vote")
    public VoteResponseDto voteAppointmentSchedule(@RequestBody VoteRequestDto voteRequest){
        Long appointmentId = voteRequest.getAppointmentId();
        Long participantId = voteRequest.getParticipantId();
        Appointment appointment = appointmentService.findAppointmentById(appointmentId);
        Participant participant = participantService.findParticipantById(participantId);

        Vote savedVote = null;
        List<PossibleScheduleResponseDto> savedPossibleScheduleList = new ArrayList<>();
        for (PossibleScheduleRequestDto possibleScheduleRequest : voteRequest.getPossibleScheduleList()) {
            PossibleSchedule possibleSchedule = new PossibleSchedule(null, appointment, possibleScheduleRequest.getStartTime(), possibleScheduleRequest.getEndTime());

            savedVote = voteService.saveVoteSchedule(new Vote(null, participant, possibleSchedule));

            PossibleScheduleResponseDto possibleScheduleResponse = new PossibleScheduleResponseDto();
            possibleScheduleResponse.setPossibleScheduleFrom(savedVote.getPossibleSchedule());
            savedPossibleScheduleList.add(possibleScheduleResponse);
        }
        VoteResponseDto result = new VoteResponseDto(appointment.getId(), savedVote.getParticipant().getId(), savedPossibleScheduleList);

        return result;
    }
}