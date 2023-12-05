package com.example.wwmeet_backend.appointment.service;


import com.example.wwmeet_backend.appointment.domain.Appointment;
import com.example.wwmeet_backend.appointment.dto.request.SaveAppointmentRequest;
import com.example.wwmeet_backend.appointment.dto.response.FindAppointmentListResponse;
import com.example.wwmeet_backend.appointment.dto.response.FindAppointmentResponse;
import com.example.wwmeet_backend.appointment.repository.AppointmentRepository;
import com.example.wwmeet_backend.participant.domain.Participant;
import com.example.wwmeet_backend.vote.domain.Vote;
import com.example.wwmeet_backend.vote.repository.VoteRepository;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final VoteRepository voteRepository;
    @Transactional
    public Long saveAppointment(SaveAppointmentRequest saveAppointmentRequest){
        Appointment savedAppointment = appointmentRepository.save(saveAppointmentRequest.toEntity());
        return savedAppointment.getId();
    }

    public FindAppointmentResponse findAppointmentById(Long id){
        Optional<Appointment> foundAppointmentOptional = appointmentRepository.findById(id);
        Appointment foundAppointment = foundAppointmentOptional.orElseThrow(() -> new NoSuchElementException());
        return FindAppointmentResponse.builder()
                .appointmentName(foundAppointment.getAppointmentName())
                .appointmentPlace(foundAppointment.getAppointmentPlace())
                .identificationCode(foundAppointment.getIdentificationCode())
                .participantNum(foundAppointment.getParticipantNum())
                .voteDeadline(foundAppointment.getVoteDeadline())
                .build();
    }

    public List<FindAppointmentListResponse> findAllAppointment(List<Long> appointmentIdList) {
        return appointmentIdList.stream()
                .map(id -> appointmentRepository.findById(id).orElseThrow(NoSuchElementException::new))
                .map(foundAppointment -> FindAppointmentListResponse.builder()
                        .id(foundAppointment.getId())
                        .appointmentName(foundAppointment.getAppointmentName())
                        .voteDeadline(foundAppointment.getVoteDeadline())
                        .build())
                .collect(Collectors.toList());
    }

    public boolean getParticipantVoteStatus(Long appointmentId, String participantName){
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(NoSuchElementException::new);
        List<Participant> participantList = appointment.getParticipantList();
        for (Participant participant : participantList) {
            if(participant.getParticipantName().equals(participantName)){
                Optional<Vote> vote = voteRepository.findByParticipant(participant);
                if(vote.isEmpty()){
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public Long findAppointmentByCode(String code) {
        Appointment appointment = appointmentRepository.findByIdentificationCode(code).orElseThrow(RuntimeException::new);
        return appointment.getId();
    }
}