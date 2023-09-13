package com.example.wwmeet_backend.controller;


import com.example.wwmeet_backend.domain.Appointment;
import com.example.wwmeet_backend.domain.Participant;
import com.example.wwmeet_backend.domain.PossibleSchedule;
import com.example.wwmeet_backend.domain.Vote;
import com.example.wwmeet_backend.dto.PossibleScheduleRequestDto;
import com.example.wwmeet_backend.dto.PossibleScheduleResponseDto;
import com.example.wwmeet_backend.dto.VoteRequestDto;
import com.example.wwmeet_backend.dto.VoteResponseDto;
import com.example.wwmeet_backend.service.AppointmentService;
import com.example.wwmeet_backend.service.ParticipantService;
import com.example.wwmeet_backend.service.VoteService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
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

        return new VoteResponseDto(appointment.getId(), savedVote.getParticipant().getId(), savedPossibleScheduleList);
    }
}