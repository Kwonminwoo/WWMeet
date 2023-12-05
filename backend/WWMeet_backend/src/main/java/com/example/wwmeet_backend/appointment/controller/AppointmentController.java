package com.example.wwmeet_backend.appointment.controller;


import com.example.wwmeet_backend.appointment.dto.request.SaveAppointmentRequest;
import com.example.wwmeet_backend.appointment.dto.response.FindAppointmentListResponse;
import com.example.wwmeet_backend.appointment.dto.response.FindAppointmentResponse;
import com.example.wwmeet_backend.appointment.dto.response.FindAppointmentVoteStatus;
import com.example.wwmeet_backend.appointment.service.AppointmentService;
import com.example.wwmeet_backend.participant.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final ParticipantService participantService;

    @PostMapping
    public Long saveAppointment(@RequestBody SaveAppointmentRequest saveAppointmentRequest){
        Long savedAppointmentId = appointmentService.saveAppointment(saveAppointmentRequest);
        participantService.addParticipantByAppointmentId(saveAppointmentRequest.getParticipantName(), savedAppointmentId);
        return savedAppointmentId;
    }

    @GetMapping("/{id}")
    public FindAppointmentResponse findAppointmentById(@PathVariable(name = "id") Long id){
        return appointmentService.findAppointmentById(id);
    }

    @GetMapping
    public List<FindAppointmentListResponse> findAllAppointment(@RequestParam List<Long> appointmentIdList){
        return appointmentService.findAllAppointment(appointmentIdList);
    }

    @GetMapping("/{id}/{name}/vote-status")
    public boolean getParticipantWithVoteStatus(
        @PathVariable("id") Long appointmentId, @PathVariable("name") String participantName){
        System.out.println(participantName);
        return appointmentService.getParticipantVoteStatus(appointmentId, participantName);
    }

    @GetMapping("/code")
    public Long findAppointmentByCode(@RequestParam String code){
        return appointmentService.findAppointmentByCode(code);
    }

}