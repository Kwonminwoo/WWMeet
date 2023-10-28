package com.example.wwmeet_backend.participant.service;

import com.example.wwmeet_backend.participant.domain.Participant;
import com.example.wwmeet_backend.participant.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipantService {
    private final ParticipantRepository participantRepository;

    public Participant findParticipantById(Long participantId) {
        return participantRepository.findById(participantId).orElseThrow(() ->
                new NoSuchElementException());
    }

    public Participant addParticipantOfAppointment(Participant participant){
        return participantRepository.save(participant);
    }

}
