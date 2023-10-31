package com.example.wwmeet_backend.participant.controller;


import com.example.wwmeet_backend.appointment.domain.Appointment;
import com.example.wwmeet_backend.appointment.service.AppointmentService;
import com.example.wwmeet_backend.participant.domain.Participant;
import com.example.wwmeet_backend.participant.dto.ParticipantAndAppointmentDto;
import com.example.wwmeet_backend.participant.dto.ParticipantResponse;
import com.example.wwmeet_backend.participant.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ParticipantController {
    private final ParticipantService participantService;
    private final AppointmentService appointmentService;

//    @PostMapping("/api/appointments/participant")
//    public Long saveParticipantOfAppointment(@RequestBody ParticipantAndAppointmentDto participantAndAppointment){
//        Appointment foundAppointment = appointmentService.findByIdentificationCode(participantAndAppointment.getAppointmentIdentificationCode());
//        Participant participant = participantService.addParticipantOfAppointment(new Participant(null, foundAppointment, participantAndAppointment.getParticipantName()));
////        return new ParticipantResponse(foundAppointment.getAppointmentName(), participant.getParticipantName());
//    }
}