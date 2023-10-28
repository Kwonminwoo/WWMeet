package com.example.wwmeet_backend.service;

import com.example.wwmeet_backend.appointment.domain.Appointment;
import com.example.wwmeet_backend.participant.domain.Participant;
import com.example.wwmeet_backend.possibleschedule.domain.PossibleSchedule;
import com.example.wwmeet_backend.vote.domain.Vote;
import com.example.wwmeet_backend.vote.repository.VoteRepository;
import com.example.wwmeet_backend.vote.service.VoteService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @Mock
    VoteRepository voteRepository;

    @Test
    void votePossibleScheduleTest(){
        Appointment appointment = new Appointment(1L, "test", "test", "test", 2, null);
        Participant participant = new Participant(1L, appointment, "나");
        PossibleSchedule possibleScheduleA = new PossibleSchedule(1L, appointment, LocalDateTime.now(), LocalDateTime.now());
//        PossibleSchedule possibleScheduleB = new PossibleSchedule(2L, appointment, LocalDateTime.now(), LocalDateTime.now());
        Vote foundVote = new Vote(1L, participant, possibleScheduleA);
        given(voteRepository.save(any()))
                .willReturn(foundVote);

        VoteService voteService = new VoteService(voteRepository);
        Vote vote = voteService.saveVoteSchedule(foundVote);

        Assertions.assertThat(vote.getId()).isEqualTo(foundVote.getId());
    }
}