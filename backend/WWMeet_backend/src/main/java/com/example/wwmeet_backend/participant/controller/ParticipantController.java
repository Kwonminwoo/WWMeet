package com.example.wwmeet_backend.participant.controller;


import com.example.wwmeet_backend.appointment.service.AppointmentService;
import com.example.wwmeet_backend.participant.dto.AddParticipantRequest;
import com.example.wwmeet_backend.participant.dto.FindParticipantResponse;
import com.example.wwmeet_backend.participant.service.ParticipantService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ParticipantController {
    private final ParticipantService participantService;

    @PostMapping("/api/appointments/participant")
    public Long saveParticipantOfAppointment(@RequestBody AddParticipantRequest addParticipantRequest){
        return participantService.addParticipantByIdentificationCode(addParticipantRequest);
    }

    @GetMapping("/api/participants/{appointment_id}")
    public List<FindParticipantResponse> findAllParticipantOfAppointment(@PathVariable("appointment_id") Long id){
        return participantService.getParticipantList(id);
    }
}