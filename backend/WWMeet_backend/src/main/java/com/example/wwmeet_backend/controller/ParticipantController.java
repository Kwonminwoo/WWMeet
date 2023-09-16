package com.example.wwmeet_backend.controller;


import com.example.wwmeet_backend.domain.Appointment;
import com.example.wwmeet_backend.domain.Participant;
import com.example.wwmeet_backend.dto.ParticipantAndAppointmentDto;
import com.example.wwmeet_backend.dto.ParticipantResponse;
import com.example.wwmeet_backend.service.AppointmentService;
import com.example.wwmeet_backend.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ParticipantController {
    private final ParticipantService participantService;
    private final AppointmentService appointmentService;

    @PostMapping("api/appointment/participant")
    public ParticipantResponse saveParticipantOfAppointment(ParticipantAndAppointmentDto participantAndAppointment){
        Appointment foundAppointment = appointmentService.findAppointmentByIdentificationCode(participantAndAppointment.getAppointmentIdentificationCode());
        Participant participant = participantService.addParticipantOfAppointment(new Participant(null, foundAppointment, participantAndAppointment.getParticipantName()));
        return new ParticipantResponse(foundAppointment.getAppointmentName(), participant.getParticipantName());
    }
}