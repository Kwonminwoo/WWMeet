package com.example.wwmeet_backend.domain.participant.service;

import com.example.wwmeet_backend.domain.appointment.entity.Appointment;
import com.example.wwmeet_backend.domain.appointment.repository.AppointmentRepository;
import com.example.wwmeet_backend.domain.member.entity.Member;
import com.example.wwmeet_backend.domain.participant.entity.Participant;
import com.example.wwmeet_backend.domain.participant.dto.AddParticipantRequest;
import com.example.wwmeet_backend.domain.participant.dto.FindParticipantResponse;
import com.example.wwmeet_backend.domain.participant.repository.ParticipantRepository;
import com.example.wwmeet_backend.domain.participant.util.ParticipantDtoMapper;
import com.example.wwmeet_backend.global.exception.DataNotFoundException;
import com.example.wwmeet_backend.global.util.CurrentMemberService;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final AppointmentRepository appointmentRepository;
    private final CurrentMemberService currentMemberService;

    public Long addParticipantByIdentificationCode(AddParticipantRequest addParticipantRequest) {
        Appointment foundAppointment = appointmentRepository.findByIdentificationCode(
            addParticipantRequest.getIdentificationCode()).orElseThrow(DataNotFoundException::new);

        Member currentMember = currentMemberService.getCurrentMember();

        Optional<Participant> participantOptional = participantRepository.findByMemberIdAndAppointment(
            currentMember.getId(), foundAppointment);

        if(participantOptional.isPresent()) {
            throw new RuntimeException("이미 참여한 약속입니다.");
        }

        participantRepository.save(
            Participant.builder()
                .appointment(foundAppointment)
                .participantName(addParticipantRequest.getParticipantName())
                .member(currentMember)
                .build()
        );

        return foundAppointment.getId();
    }

    public void addParticipantByAppointmentId(String participantName, Long id) {
        Appointment targetAppointment = appointmentRepository.findById(id)
            .orElseThrow(RuntimeException::new);

        participantRepository.save(ParticipantDtoMapper.toEntity(participantName, targetAppointment,
            currentMemberService.getCurrentMember()));
    }

    public List<FindParticipantResponse> getParticipantList(Long id) {
        List<Participant> foundParticipantList = participantRepository.findByAppointmentId(id);
        List<FindParticipantResponse> responseList = new ArrayList<>();
        for (Participant participant : foundParticipantList) {
            FindParticipantResponse response = new FindParticipantResponse(
                participant.getParticipantName(),
                participant.getVoteList().size() != 0);
            responseList.add(response);
        }

        return responseList;
    }

}
