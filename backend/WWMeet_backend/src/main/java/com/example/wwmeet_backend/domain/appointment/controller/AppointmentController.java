package com.example.wwmeet_backend.domain.appointment.controller;


import com.example.wwmeet_backend.domain.appointment.dto.request.SaveAppointmentRequest;
import com.example.wwmeet_backend.domain.appointment.dto.response.AppointmentScheduleResponse;
import com.example.wwmeet_backend.domain.appointment.dto.response.FindAppointmentListResponse;
import com.example.wwmeet_backend.domain.appointment.dto.response.FindAppointmentResponse;
import com.example.wwmeet_backend.domain.appointment.service.AppointmentService;
import com.example.wwmeet_backend.domain.participant.service.ParticipantService;
import com.example.wwmeet_backend.global.util.CurrentMemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final ParticipantService participantService;
    private final CurrentMemberService currentMemberService;

    @PostMapping
    public Long saveAppointment(@RequestBody SaveAppointmentRequest saveAppointmentRequest) {
        Long savedAppointmentId = appointmentService.saveAppointment(saveAppointmentRequest);
        participantService.addParticipantByAppointmentId(
            saveAppointmentRequest.getParticipantName(), savedAppointmentId);
        return savedAppointmentId;
    }

    @GetMapping("/{id}")
    public FindAppointmentResponse findAppointmentById(@PathVariable(name = "id") Long id) {
        return appointmentService.findAppointmentById(id);
    }

    @GetMapping
    public List<FindAppointmentListResponse> findAllAppointment() {
        return appointmentService.findAllAppointment(currentMemberService.getCurrentMember());
    }

    @GetMapping("/{id}/{name}/vote-status")
    public boolean getParticipantWithVoteStatus(
        @PathVariable("id") Long appointmentId, @PathVariable("name") String participantName) {
        System.out.println(participantName);
        return appointmentService.getParticipantVoteStatus(appointmentId, participantName);
    }

    @GetMapping("/code")
    public Long findAppointmentByCode(@RequestParam String code) {
        return appointmentService.findAppointmentByCode(code);
    }

    @GetMapping("/{id}/date")
    public AppointmentScheduleResponse getAppointmentDate(@PathVariable("id") Long id) {
        return appointmentService.getAppointmentDate(id);
    }

}