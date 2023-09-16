package com.example.wwmeet_backend.service;

import com.example.wwmeet_backend.domain.Appointment;
import com.example.wwmeet_backend.domain.Participant;
import com.example.wwmeet_backend.repository.ParticipantRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ParticipantServiceTest {
    @Mock
    ParticipantRepository participantRepository;

    @Test
    void addParticipantOfAppointment(){
        Appointment appointment = new Appointment(1L, "test", "test", "test", 2, null);
        Participant participant = new Participant(1L, appointment, "participant1");
        given(participantRepository.save(any()))
                .willReturn(participant);

        ParticipantService participantService = new ParticipantService(participantRepository);
        Participant savedParticipant = participantService.addParticipantOfAppointment(participant);

        Assertions.assertThat(participant.getParticipantName()).isEqualTo(savedParticipant.getParticipantName());
    }
}