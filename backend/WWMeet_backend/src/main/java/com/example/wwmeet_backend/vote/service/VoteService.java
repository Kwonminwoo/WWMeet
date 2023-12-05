package com.example.wwmeet_backend.vote.service;


import com.example.wwmeet_backend.appointment.domain.Appointment;
import com.example.wwmeet_backend.appointment.repository.AppointmentRepository;
import com.example.wwmeet_backend.appointment.service.AppointmentService;
import com.example.wwmeet_backend.appointmentDate.repository.AppointmentDateRepository;
import com.example.wwmeet_backend.appointmentDate.service.AppointmentDateService;
import com.example.wwmeet_backend.participant.domain.Participant;
import com.example.wwmeet_backend.participant.repository.ParticipantRepository;
import com.example.wwmeet_backend.possibleschedule.domain.PossibleSchedule;
import com.example.wwmeet_backend.possibleschedule.dto.SavePossibleScheduleRequest;
import com.example.wwmeet_backend.possibleschedule.repository.PossibleScheduleRepository;
import com.example.wwmeet_backend.sse.domain.DefaultSseConnectionPool;
import com.example.wwmeet_backend.sse.domain.SseConnectionPool;
import com.example.wwmeet_backend.sse.domain.UserSseConnection;
import com.example.wwmeet_backend.vote.domain.Vote;
import com.example.wwmeet_backend.vote.dto.SaveVoteRequest;
import com.example.wwmeet_backend.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VoteService {
    private final VoteRepository voteRepository;
    private final ParticipantRepository participantRepository;
    private final PossibleScheduleRepository possibleScheduleRepository;
    private final AppointmentRepository appointmentRepository;
    private final SseConnectionPool<String, UserSseConnection> sseConnectionPool;
    private final AppointmentService appointmentService;
    private final AppointmentDateRepository appointmentDateRepository;

    public Long saveVoteSchedule(Long id, SaveVoteRequest saveVoteRequest) {
        Appointment foundAppointment = appointmentRepository.findById(id).orElseThrow(NoSuchElementException::new);
        Participant foundParticipant = participantRepository.findByParticipantName(foundAppointment, saveVoteRequest.getParticipantName())
                .orElseThrow(NoSuchElementException::new);

        List<PossibleSchedule> possibleScheduleList = new ArrayList<>();
        for (SavePossibleScheduleRequest possibleScheduleRequest : saveVoteRequest.getPossibleScheduleList()) {
            PossibleSchedule savedPossibleSchedule = possibleScheduleRepository.save(
                    PossibleSchedule.of(null, foundAppointment, possibleScheduleRequest.getStartDateTime(), possibleScheduleRequest.getEndDateTime())
            );
            possibleScheduleList.add(savedPossibleSchedule);
        }

        possibleScheduleList.stream()
                .map(possibleSchedule -> (Vote.of(null, foundParticipant, possibleSchedule)))
                .forEach(voteRepository::save);



        return foundAppointment.getId();
    }

    public void checkVoteCompleteAndSendMessage(Appointment appointment){

        List<Participant> participantList = appointment.getParticipantList();
        if(participantList.size() < appointment.getParticipantNum()){
            return;
        }

        for (Participant p : participantList) {
            Optional<Vote> participantOptional = voteRepository.findByParticipant(p);
            if (participantOptional.isEmpty()) {
                return;
            }
            String key = appointment.getId() + p.getParticipantName();

            UserSseConnection connection = sseConnectionPool.getConnection(key);
            connection.sendMessage("complete");
        }
    }

    public void checkVoteFinishAndSetAppointmentDate(Appointment appointment){
        if(appointmentService.checkVoteState(appointment)){
            // 완료

        }
    }
}
