package com.example.wwmeet_backend.participant.service;

import com.example.wwmeet_backend.appointment.domain.Appointment;
import com.example.wwmeet_backend.appointment.repository.AppointmentRepository;
import com.example.wwmeet_backend.participant.domain.Participant;
import com.example.wwmeet_backend.participant.dto.AddParticipantRequest;
import com.example.wwmeet_backend.participant.dto.FindParticipantResponse;
import com.example.wwmeet_backend.participant.repository.ParticipantRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    private final AppointmentRepository appointmentRepository;

    public Long addParticipantByIdentificationCode(AddParticipantRequest addParticipantRequest) {
        Appointment foundAppointment = appointmentRepository.findByIdentificationCode(addParticipantRequest.getIdentificationCode()).orElseThrow(NoSuchElementException::new);
        participantRepository.save(Participant.of(foundAppointment, addParticipantRequest.getParticipantName()));
        return foundAppointment.getId();
    }

    public void addParticipantByAppointmentId(String participantName, Long id){
        Participant participant = Participant.of(appointmentRepository.findById(id).orElseThrow(NoSuchElementException::new), participantName);
        participantRepository.save(participant);
    }

    public List<FindParticipantResponse> getParticipantList(Long id){
        List<Participant> foundParticipantList = participantRepository.findByAppointmentId(id);
        List<FindParticipantResponse> responseList = new ArrayList<>();
        for (Participant participant : foundParticipantList) {
            FindParticipantResponse response = new FindParticipantResponse(participant.getParticipantName(), participant.getVoteList().size() != 0 ? true : false);
            responseList.add(response);
        }

        return responseList;
    }

}
