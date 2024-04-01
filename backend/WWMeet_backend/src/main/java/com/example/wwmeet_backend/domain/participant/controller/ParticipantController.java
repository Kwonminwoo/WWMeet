package com.example.wwmeet_backend.domain.participant.controller;


import com.example.wwmeet_backend.domain.participant.dto.AddParticipantRequest;
import com.example.wwmeet_backend.domain.participant.service.ParticipantService;
import com.example.wwmeet_backend.global.response.ResponseAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseAPI> saveParticipantOfAppointment(
        @RequestBody AddParticipantRequest addParticipantRequest) {
        return ResponseEntity.ok(ResponseAPI.response("참가자 추가 성공",
            participantService.addParticipantByIdentificationCode(addParticipantRequest)));
    }

    @GetMapping("/api/participants/{appointment_id}")
    public ResponseEntity<ResponseAPI> findAllParticipantOfAppointment(
        @PathVariable("appointment_id") Long id) {
        return ResponseEntity.ok(ResponseAPI.response("모든 참가자 조회 성공",
            participantService.getParticipantList(id)));
    }
}