package com.example.wwmeet_backend.domain.appointment.controller;


import com.example.wwmeet_backend.domain.appointment.dto.request.SaveAppointmentRequest;
import com.example.wwmeet_backend.domain.appointment.dto.response.AppointmentScheduleResponse;
import com.example.wwmeet_backend.domain.appointment.dto.response.FindAppointmentListResponse;
import com.example.wwmeet_backend.domain.appointment.dto.response.FindAppointmentResponse;
import com.example.wwmeet_backend.domain.appointment.service.AppointmentService;
import com.example.wwmeet_backend.domain.participant.service.ParticipantService;
import com.example.wwmeet_backend.global.response.ResponseAPI;
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
    public ResponseEntity<ResponseAPI> saveAppointment(@RequestBody SaveAppointmentRequest saveAppointmentRequest) {
        Long savedAppointmentId = appointmentService.saveAppointment(saveAppointmentRequest);

        participantService.addParticipantByAppointmentId(
            saveAppointmentRequest.getParticipantName(), savedAppointmentId
        );
        return ResponseEntity.ok(ResponseAPI.response("약속 저장 성공", savedAppointmentId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseAPI> findAppointmentById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(ResponseAPI.response("약속 단건 조회 성공",
                appointmentService.findAppointmentById(id)));
    }

    @GetMapping
    public ResponseEntity<ResponseAPI> findAllAppointment() {
        return ResponseEntity.ok(ResponseAPI.response("약속 전체 조회 성공",
            appointmentService.findAllAppointment()));
    }

    @GetMapping("/{id}/{participantName}/vote-status")
    public ResponseEntity<ResponseAPI> getParticipantWithVoteStatus(
        @PathVariable("id") Long appointmentId, @PathVariable String participantName) {
        return ResponseEntity.ok(ResponseAPI.response("투표 상태 조회 성공",
            appointmentService.getParticipantVoteStatus(appointmentId, participantName)));
    }

    @GetMapping("/{id}/date")
    public ResponseEntity<ResponseAPI> getAppointmentDate(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ResponseAPI.response("약속 날짜 조회 성공",
            appointmentService.getAppointmentDate(id)));
    }

}