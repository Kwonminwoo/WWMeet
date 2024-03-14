package com.example.wwmeet_backend.domain.appointment.service;


import com.example.wwmeet_backend.domain.appointment.domain.Appointment;
import com.example.wwmeet_backend.domain.appointment.dto.request.SaveAppointmentRequest;
import com.example.wwmeet_backend.domain.appointment.dto.response.AppointmentScheduleResponse;
import com.example.wwmeet_backend.domain.appointment.dto.response.DateRangeResponse;
import com.example.wwmeet_backend.domain.appointment.dto.response.FindAppointmentListResponse;
import com.example.wwmeet_backend.domain.appointment.dto.response.FindAppointmentResponse;
import com.example.wwmeet_backend.domain.appointment.repository.AppointmentRepository;
import com.example.wwmeet_backend.domain.appointmentDate.domain.AppointmentDate;
import com.example.wwmeet_backend.domain.appointmentDate.repository.AppointmentDateRepository;
import com.example.wwmeet_backend.domain.participant.domain.Participant;
import com.example.wwmeet_backend.domain.participant.repository.ParticipantRepository;
import com.example.wwmeet_backend.domain.vote.domain.Vote;
import com.example.wwmeet_backend.domain.vote.repository.VoteRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentDateRepository appointmentDateRepository;
    private final ParticipantRepository participantRepository;
    private final VoteRepository voteRepository;

    @Transactional
    public Long saveAppointment(SaveAppointmentRequest saveAppointmentRequest) {
        Appointment savedAppointment = appointmentRepository.save(
            saveAppointmentRequest.toEntity());
        return savedAppointment.getId();
    }

    public FindAppointmentResponse findAppointmentById(Long id) {
        Optional<Appointment> foundAppointmentOptional = appointmentRepository.findById(id);
        Appointment foundAppointment = foundAppointmentOptional.orElseThrow(
            () -> new NoSuchElementException());
        return FindAppointmentResponse.builder()
            .appointmentName(foundAppointment.getName())
            .appointmentPlace(foundAppointment.getPlace())
            .identificationCode(foundAppointment.getIdentificationCode())
            .participantNum(foundAppointment.getParticipantNum())
            .voteDeadline(foundAppointment.getVoteDeadline())
            .build();
    }

    public List<FindAppointmentListResponse> findAllAppointment(List<Long> appointmentIdList) {
        List<Appointment> foundAppointmentList = new ArrayList<>();
        for (Long id : appointmentIdList) {
            foundAppointmentList.add(
                appointmentRepository.findById(id).orElseThrow(NoSuchElementException::new));
        }

        List<FindAppointmentListResponse> responseList = new ArrayList<>();
        for (Appointment foundAppointment : foundAppointmentList) {
            boolean voteState = checkVoteState(foundAppointment);
            String appointmentDate = null;
            if (voteState) {
                List<AppointmentDate> foundAppointmentDate = appointmentDateRepository.findByAppointmentId(
                    foundAppointment.getId());
                AppointmentDate firstDate = foundAppointmentDate.get(0);
                String startDate = firstDate.getStartDate().toString();
                appointmentDate =
                    startDate.substring(0, startDate.length() - 3) + "시 ~ " + firstDate.getEndDate()
                        .toLocalTime();
            }
            responseList.add(FindAppointmentListResponse.builder()
                .id(foundAppointment.getId())
                .appointmentName(foundAppointment.getName())
                .voteDeadline(foundAppointment.getVoteDeadline())
                .voteFinish(checkVoteState(foundAppointment))
                .appointmentDate(appointmentDate)
                .build());
        }

        return responseList;
    }

    public boolean getParticipantVoteStatus(Long appointmentId, String participantName) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(NoSuchElementException::new);
        List<Participant> participantList = appointment.getParticipantList();
        for (Participant participant : participantList) {
            if (participant.getParticipantName().equals(participantName)) {
                Optional<Vote> vote = voteRepository.findByParticipant(participant);
                return vote.isPresent();
            }
        }
        return false;
    }

    public Long findAppointmentByCode(String code) {
        Appointment appointment = appointmentRepository.findByIdentificationCode(code)
            .orElseThrow(RuntimeException::new);
        return appointment.getId();
    }

    public boolean checkVoteState(Appointment appointment) {
        List<Participant> participantList = appointment.getParticipantList();
        if (participantList.size() != appointment.getParticipantNum()) {
            return false;
        }
        for (Participant participant : participantList) {
            if (participant.getVoteList().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public AppointmentScheduleResponse getAppointmentDate(Long id) {
        List<AppointmentDate> appointmentDateList = appointmentDateRepository.findByAppointmentId(
            id);

        List<DateRangeResponse> dateRangeResponseList = appointmentDateList.stream()
            .map(date -> {
                return new DateRangeResponse(date.getStartDate(), date.getEndDate().toLocalTime());
            })
            .toList();

        AppointmentScheduleResponse appointmentScheduleResponse = new AppointmentScheduleResponse();
        appointmentScheduleResponse.setFirstSchedule(dateRangeResponseList.get(0));

        if (dateRangeResponseList.size() == 1) {
            appointmentScheduleResponse.setSecondSchedule(null);
            appointmentScheduleResponse.setThirdSchedule(null);
        } else if (dateRangeResponseList.size() == 2) {
            appointmentScheduleResponse.setSecondSchedule(dateRangeResponseList.get(1));
            appointmentScheduleResponse.setThirdSchedule(null);
        } else {
            appointmentScheduleResponse.setSecondSchedule(dateRangeResponseList.get(1));
            appointmentScheduleResponse.setThirdSchedule(dateRangeResponseList.get(2));
        }

        return appointmentScheduleResponse;
    }
}