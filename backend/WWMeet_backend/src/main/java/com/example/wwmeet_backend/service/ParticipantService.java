package com.example.wwmeet_backend.service;

import com.example.wwmeet_backend.domain.Participant;
import com.example.wwmeet_backend.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

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
