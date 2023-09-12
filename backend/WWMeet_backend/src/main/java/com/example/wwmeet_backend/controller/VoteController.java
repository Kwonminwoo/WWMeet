package com.example.wwmeet_backend.controller;


import com.example.wwmeet_backend.domain.Appointment;
import com.example.wwmeet_backend.domain.Participant;
import com.example.wwmeet_backend.domain.PossibleSchedule;
import com.example.wwmeet_backend.domain.Vote;
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
    public List<VoteResponseDto> voteAppointmentSchedule(@RequestBody List<VoteRequestDto> voteList){
        Long appointmentId = voteList.get(0).getAppointmentId();
        Long participantId = voteList.get(0).getParticipantId();
        Appointment appointment = appointmentService.findAppointmentById(appointmentId);
        Participant participant = participantService.findParticipantById(participantId);
        List<VoteResponseDto> voteResponseList = new ArrayList<>();
        for (VoteRequestDto voteRequest : voteList) {
            PossibleSchedule possibleSchedule = new PossibleSchedule(null, appointment, voteRequest.getStartTime(), voteRequest.getEndTime());
            Vote savedVote = voteService.saveVoteSchedule(new Vote(null, participant, possibleSchedule));
            voteResponseList.add(new VoteResponseDto(savedVote.getId(), participantId, savedVote.getPossibleSchedule().getStartTime(), savedVote.getPossibleSchedule().getEndTime()));
        }

        return voteResponseList;
    }
}