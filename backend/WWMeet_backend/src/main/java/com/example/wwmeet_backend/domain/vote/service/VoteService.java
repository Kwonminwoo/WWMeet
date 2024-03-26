package com.example.wwmeet_backend.domain.vote.service;


import com.example.wwmeet_backend.domain.appointment.entity.Appointment;
import com.example.wwmeet_backend.domain.appointment.repository.AppointmentRepository;
import com.example.wwmeet_backend.domain.appointment.service.AppointmentService;
import com.example.wwmeet_backend.domain.appointmentDate.service.AppointmentDateService;
import com.example.wwmeet_backend.domain.participant.entity.Participant;
import com.example.wwmeet_backend.domain.participant.repository.ParticipantRepository;
import com.example.wwmeet_backend.domain.possibleschedule.entity.PossibleSchedule;
import com.example.wwmeet_backend.domain.possibleschedule.dto.SavePossibleScheduleRequest;
import com.example.wwmeet_backend.domain.possibleschedule.repository.PossibleScheduleRepository;
import com.example.wwmeet_backend.domain.vote.entity.Vote;
import com.example.wwmeet_backend.domain.vote.dto.SaveVoteRequest;
import com.example.wwmeet_backend.domain.vote.repository.VoteRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VoteService {

    private final VoteRepository voteRepository;
    private final ParticipantRepository participantRepository;
    private final PossibleScheduleRepository possibleScheduleRepository;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentService appointmentService;
    private final AppointmentDateService appointmentDateService;

    public Long saveVoteSchedule(Long id, SaveVoteRequest saveVoteRequest) {
        Appointment foundAppointment = appointmentRepository.findById(id)
            .orElseThrow(NoSuchElementException::new);
        Participant foundParticipant = participantRepository.findByParticipantName(foundAppointment,
                saveVoteRequest.getParticipantName())
            .orElseThrow(NoSuchElementException::new);

        List<PossibleSchedule> possibleScheduleList = new ArrayList<>();
        for (SavePossibleScheduleRequest possibleScheduleRequest : saveVoteRequest.getPossibleScheduleList()) {
            PossibleSchedule savedPossibleSchedule = possibleScheduleRepository.save(
                PossibleSchedule.of(null, foundAppointment,
                    possibleScheduleRequest.getStartDateTime(),
                    possibleScheduleRequest.getEndDateTime())
            );
            possibleScheduleList.add(savedPossibleSchedule);
        }

        possibleScheduleList.stream()
            .map(possibleSchedule -> (Vote.of(null, foundParticipant, possibleSchedule)))
            .forEach(voteRepository::saveAndFlush);

        checkVoteFinishAndSetAppointmentDate(foundAppointment);

        return foundAppointment.getId();
    }

    public void checkVoteFinishAndSetAppointmentDate(Appointment appointment) {
        if (appointmentService.checkVoteState(appointment)) {
            appointmentDateService.setAppointmentDate(appointment);
        }
    }


}
