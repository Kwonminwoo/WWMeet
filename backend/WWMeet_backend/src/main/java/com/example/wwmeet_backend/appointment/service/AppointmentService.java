package com.example.wwmeet_backend.appointment.service;


import com.example.wwmeet_backend.appointment.domain.Appointment;
import com.example.wwmeet_backend.appointment.dto.request.SaveAppointmentRequest;
import com.example.wwmeet_backend.appointment.dto.response.AppointmentScheduleResponse;
import com.example.wwmeet_backend.appointment.dto.response.DateRangeResponse;
import com.example.wwmeet_backend.appointment.dto.response.FindAppointmentListResponse;
import com.example.wwmeet_backend.appointment.dto.response.FindAppointmentResponse;
import com.example.wwmeet_backend.appointment.repository.AppointmentRepository;
import com.example.wwmeet_backend.appointmentDate.domain.AppointmentDate;
import com.example.wwmeet_backend.appointmentDate.repository.AppointmentDateRepository;
import com.example.wwmeet_backend.participant.domain.Participant;
import com.example.wwmeet_backend.participant.repository.ParticipantRepository;
import com.example.wwmeet_backend.vote.domain.Vote;
import com.example.wwmeet_backend.vote.repository.VoteRepository;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentDateRepository appointmentDateRepository;
    private final ParticipantRepository participantRepository;
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
        List<Appointment> foundAppointmentList = new ArrayList<>();
        for (Long id : appointmentIdList) {
            foundAppointmentList.add(appointmentRepository.findById(id).orElseThrow(NoSuchElementException::new));
        }

        List<FindAppointmentListResponse> responseList = new ArrayList<>();
        for(Appointment foundAppointment : foundAppointmentList){
            boolean voteState = checkVoteState(foundAppointment);
            String appointmentDate = null;
            if(voteState){
                List<AppointmentDate> foundAppointmentDate = appointmentDateRepository.findByAppointmentId(
                    foundAppointment.getId());
                AppointmentDate firstDate = foundAppointmentDate.get(0);
                String startDate = firstDate.getStartDate().toString();
                appointmentDate = startDate.substring(0, startDate.length() - 3) + "시 ~ " + firstDate.getEndDate().toLocalTime();
            }
            responseList.add(FindAppointmentListResponse.builder()
                .id(foundAppointment.getId())
                .appointmentName(foundAppointment.getAppointmentName())
                .voteDeadline(foundAppointment.getVoteDeadline())
                .voteFinish(checkVoteState(foundAppointment))
                .appointmentDate(appointmentDate)
                .build());
        }

        return responseList;
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

    public boolean checkVoteState(Appointment appointment){
        List<Participant> participantList = appointment.getParticipantList();
        if(participantList.size() != appointment.getParticipantNum()){
            return false;
        }
        for (Participant participant : participantList) {
            if(participant.getVoteList().isEmpty()){
                return false;
            }
        }
        return true;
    }

    public AppointmentScheduleResponse getAppointmentDate(Long id){
        List<AppointmentDate> appointmentDateList = appointmentDateRepository.findByAppointmentId(id);

        List<DateRangeResponse> dateRangeResponseList = appointmentDateList.stream()
            .map(date -> {
                return new DateRangeResponse(date.getStartDate(), date.getEndDate().toLocalTime());
            })
            .toList();

        AppointmentScheduleResponse appointmentScheduleResponse = new AppointmentScheduleResponse();
        appointmentScheduleResponse.setFirstSchedule(dateRangeResponseList.get(0));

        if(dateRangeResponseList.size() == 1){
            appointmentScheduleResponse.setSecondSchedule(null);
            appointmentScheduleResponse.setThirdSchedule(null);
        }else if(dateRangeResponseList.size() == 2){
            appointmentScheduleResponse.setSecondSchedule(dateRangeResponseList.get(1));
            appointmentScheduleResponse.setThirdSchedule(null);
        }else{
            appointmentScheduleResponse.setSecondSchedule(dateRangeResponseList.get(1));
            appointmentScheduleResponse.setThirdSchedule(dateRangeResponseList.get(2));
        }

        return appointmentScheduleResponse;
    }
}