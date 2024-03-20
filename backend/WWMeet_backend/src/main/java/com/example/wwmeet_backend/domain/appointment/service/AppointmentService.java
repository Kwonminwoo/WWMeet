package com.example.wwmeet_backend.domain.appointment.service;


import com.example.wwmeet_backend.domain.appointment.domain.Appointment;
import com.example.wwmeet_backend.domain.appointment.dto.request.SaveAppointmentRequest;
import com.example.wwmeet_backend.domain.appointment.dto.response.AppointmentScheduleResponse;
import com.example.wwmeet_backend.domain.appointment.dto.response.DateRangeResponse;
import com.example.wwmeet_backend.domain.appointment.dto.response.FindAppointmentListResponse;
import com.example.wwmeet_backend.domain.appointment.dto.response.FindAppointmentResponse;
import com.example.wwmeet_backend.domain.appointment.repository.AppointmentRepository;
import com.example.wwmeet_backend.domain.appointment.util.AppointmentDtoMapper;
import com.example.wwmeet_backend.domain.appointmentDate.domain.AppointmentDate;
import com.example.wwmeet_backend.domain.appointmentDate.repository.AppointmentDateRepository;
import com.example.wwmeet_backend.domain.member.domain.Member;
import com.example.wwmeet_backend.domain.participant.domain.Participant;
import com.example.wwmeet_backend.domain.participant.repository.ParticipantRepository;
import com.example.wwmeet_backend.domain.vote.domain.Vote;
import com.example.wwmeet_backend.domain.vote.repository.VoteRepository;
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
@Transactional(readOnly = true)
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentDateRepository appointmentDateRepository;
    private final ParticipantRepository participantRepository;
    private final VoteRepository voteRepository;
    private final CurrentMemberService currentMemberService;

    @Transactional
    public Long saveAppointment(SaveAppointmentRequest saveAppointmentRequest) {
        Member currentMember = currentMemberService.getCurrentMember();

        Appointment savedAppointment = appointmentRepository.save(
            AppointmentDtoMapper.toEntity(saveAppointmentRequest, currentMember));

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

    public List<FindAppointmentListResponse> findAllAppointment() {
        Long memberId = currentMemberService.getCurrentMember().getId();
        List<Appointment> foundAppointmentList =
            appointmentRepository.findAppointmentByMemberId(memberId);

        List<FindAppointmentListResponse> responseList = new ArrayList<>();

        for (Appointment appointment : foundAppointmentList) {
            Participant participant = participantRepository.findByAppointmentIdAndMemberId(
                    appointment.getId(), memberId).orElseThrow(RuntimeException::new);
            FindAppointmentListResponse response;
            if(checkVoteState(appointment)){
                AppointmentDate appointmentDate = appointmentDateRepository.findByAppointmentId(
                    appointment.getId()).orElseThrow(RuntimeException::new);

                response = FindAppointmentListResponse.builder()
                    .id(appointment.getId())
                    .appointmentName(appointment.getName())
                    .voteFinish(true)
                    .appointmentDate(appointmentDate.toString())
                    .participantName(participant.getParticipantName())
                    .build();
            } else {
                response = FindAppointmentListResponse.builder()
                    .id(appointment.getId())
                    .appointmentName(appointment.getName())
                    .voteDeadline(appointment.getVoteDeadline())
                    .voteFinish(false)
                    .participantName(participant.getParticipantName())
                    .build();
            }
            responseList.add(response);
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
        List<AppointmentDate> appointmentDateList = appointmentDateRepository.findAllByAppointmentId(
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