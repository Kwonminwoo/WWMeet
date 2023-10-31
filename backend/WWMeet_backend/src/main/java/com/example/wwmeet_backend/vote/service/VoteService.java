package com.example.wwmeet_backend.vote.service;


import com.example.wwmeet_backend.appointment.repository.AppointmentRepository;
import com.example.wwmeet_backend.participant.domain.Participant;
import com.example.wwmeet_backend.participant.repository.ParticipantRepository;
import com.example.wwmeet_backend.possibleschedule.domain.PossibleSchedule;
import com.example.wwmeet_backend.possibleschedule.dto.SavePossibleScheduleRequest;
import com.example.wwmeet_backend.possibleschedule.repository.PossibleScheduleRepository;
import com.example.wwmeet_backend.vote.domain.Vote;
import com.example.wwmeet_backend.vote.dto.SaveVoteRequest;
import com.example.wwmeet_backend.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VoteService {
    private final VoteRepository voteRepository;
    private final ParticipantRepository participantRepository;
    private final PossibleScheduleRepository possibleScheduleRepository;
    public Long saveVoteSchedule(SaveVoteRequest saveVoteRequest) {
        Participant foundParticipant = participantRepository.findByParticipantName(saveVoteRequest.getParticipantName());

        List<PossibleSchedule> possibleScheduleList = new ArrayList<>();
        for (SavePossibleScheduleRequest possibleScheduleRequest : saveVoteRequest.getPossibleScheduleList()) {
            PossibleSchedule savedPossibleSchedule = possibleScheduleRepository.save(
                    PossibleSchedule.of(null, foundParticipant.getAppointment(), possibleScheduleRequest.getStartTime(), possibleScheduleRequest.getEndTime())
            );
            possibleScheduleList.add(savedPossibleSchedule);
        }

        possibleScheduleList.stream()
                .map(possibleSchedule -> (Vote.of(null, foundParticipant, possibleSchedule)))
                .forEach(voteRepository::save);

        return foundParticipant.getAppointment().getId();
    }
}
