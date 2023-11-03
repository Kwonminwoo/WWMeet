package com.example.wwmeet_backend.participant.service;

import com.example.wwmeet_backend.appointment.repository.AppointmentRepository;
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
    private final AppointmentRepository appointmentRepository;

    public Participant findParticipantById(Long participantId) {
        return participantRepository.findById(participantId).orElseThrow(() ->
                new NoSuchElementException());
    }

    public void saveParticipantWithAppointment(String participantName, Long id){
        Participant participant = Participant.of(id, appointmentRepository.findById(id).orElseThrow(NoSuchElementException::new), participantName);
        participantRepository.save(participant);
    }

}
