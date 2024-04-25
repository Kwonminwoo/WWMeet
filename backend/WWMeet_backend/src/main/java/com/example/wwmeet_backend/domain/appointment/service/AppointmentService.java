package com.example.wwmeet_backend.domain.appointment.service;


import com.example.wwmeet_backend.domain.appointment.entity.Appointment;
import com.example.wwmeet_backend.domain.appointment.dto.request.SaveAppointmentRequest;
import com.example.wwmeet_backend.domain.appointment.dto.response.AppointmentScheduleResponse;
import com.example.wwmeet_backend.domain.appointment.dto.response.DateRangeResponse;
import com.example.wwmeet_backend.domain.appointment.dto.response.FindAppointmentListResponse;
import com.example.wwmeet_backend.domain.appointment.dto.response.FindAppointmentResponse;
import com.example.wwmeet_backend.domain.appointment.repository.AppointmentRepository;
import com.example.wwmeet_backend.domain.appointment.util.AppointmentDtoMapper;
import com.example.wwmeet_backend.domain.appointmentDate.entity.AppointmentDate;
import com.example.wwmeet_backend.domain.appointmentDate.repository.AppointmentDateRepository;
import com.example.wwmeet_backend.domain.member.entity.Member;
import com.example.wwmeet_backend.domain.participant.entity.Participant;
import com.example.wwmeet_backend.domain.participant.repository.ParticipantRepository;
import com.example.wwmeet_backend.domain.vote.entity.Vote;
import com.example.wwmeet_backend.domain.vote.repository.VoteRepository;
import com.example.wwmeet_backend.global.exception.DataNotFoundException;
import com.example.wwmeet_backend.global.util.CurrentMemberService;
import java.util.ArrayList;
import java.util.List;
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
            DataNotFoundException::new);

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

        List<Participant> participantList = participantRepository.findAllByMemberId(memberId);

        List<FindAppointmentListResponse> responseList = new ArrayList<>();
        for (Participant participant : participantList) {
            Appointment appointment = participant.getAppointment();

            FindAppointmentListResponse response;
            if(checkVoteState(appointment)){
                String appointmentDate = getAppointmentDate(appointment.getId()).getFirstSchedule()
                    .getStartDateTime().toString();

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
                    .build();   // ToDo: voteFinish만 if else 문에서 해결
            }
            responseList.add(response);
        }

        return responseList;
    }

    public boolean getParticipantVoteStatus(Long appointmentId, String participantName) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(DataNotFoundException::new);

        List<Participant> participantList = appointment.getParticipantList();
        for (Participant participant : participantList) {
            if (participant.getParticipantName().equals(participantName)) {
                Optional<Vote> vote = voteRepository.findByParticipant(participant);
                return vote.isPresent();
            }
        }

        return false;
    }

    public boolean checkVoteState(Appointment appointment) { // ToDo: public 으로 되어 있는거 private으로 변경
        List<Participant> participantList = appointment.getParticipantList();
        if (participantList.size() != appointment.getParticipantNum()) { // TODO: 약속 투표 상태 조회 시 성능 하락
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