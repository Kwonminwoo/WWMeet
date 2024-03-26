package com.example.wwmeet_backend.domain.appointment.controller;


import com.example.wwmeet_backend.domain.appointment.dto.request.SaveAppointmentRequest;
import com.example.wwmeet_backend.domain.appointment.dto.response.AppointmentScheduleResponse;
import com.example.wwmeet_backend.domain.appointment.dto.response.FindAppointmentListResponse;
import com.example.wwmeet_backend.domain.appointment.dto.response.FindAppointmentResponse;
import com.example.wwmeet_backend.domain.appointment.service.AppointmentService;
import com.example.wwmeet_backend.domain.participant.service.ParticipantService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final ParticipantService participantService;

    @PostMapping
    public ResponseEntity<Long> saveAppointment(@RequestBody SaveAppointmentRequest saveAppointmentRequest) {
        Long savedAppointmentId = appointmentService.saveAppointment(saveAppointmentRequest);

        participantService.addParticipantByAppointmentId(
            saveAppointmentRequest.getParticipantName(), savedAppointmentId
        );
        return ResponseEntity.ok(savedAppointmentId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FindAppointmentResponse> findAppointmentById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(appointmentService.findAppointmentById(id));
    }

    @GetMapping
    public ResponseEntity<List<FindAppointmentListResponse>> findAllAppointment() {
        return ResponseEntity.ok(appointmentService.findAllAppointment());
    }

    @GetMapping("/{id}/{participantName}/vote-status")
    public ResponseEntity<Boolean> getParticipantWithVoteStatus(
        @PathVariable("id") Long appointmentId, @PathVariable String participantName) {
        return ResponseEntity.ok(appointmentService.getParticipantVoteStatus(appointmentId, participantName));
    }

    @GetMapping("/{id}/date")
    public ResponseEntity<AppointmentScheduleResponse> getAppointmentDate(@PathVariable("id") Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentDate(id));
    }

}