package com.example.wwmeet_backend.appointment.controller;


import com.example.wwmeet_backend.appointment.dto.AppointmentRequestDto;
import com.example.wwmeet_backend.appointment.dto.AppointmentResponseDto;
import com.example.wwmeet_backend.appointment.dto.SaveAppointmentResponseDto;
import com.example.wwmeet_backend.appointment.service.AppointmentService;
import com.example.wwmeet_backend.participant.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final ParticipantService participantService;
    @GetMapping("/api/appointments")
    public List<AppointmentResponseDto> findAllAppointment(@RequestParam List<String> appointmentCodeList){
//        List<Appointment> findAppointmentList = appointmentService.findAllAppointment(appointmentCodeList);
//        List<AppointmentResponseDto> findAppointmentDtoList = new ArrayList<>();
//        for (Appointment appointment : findAppointmentList) {
//            findAppointmentDtoList.add(appointmentMapper.toResponseDto(appointment));
//        }
//        return findAppointmentDtoList;
        return null;
    }

    @GetMapping("/api/appointment/{appointment_id}")
    public AppointmentResponseDto findAppointmentById(@PathVariable(name = "appointment_id") Long appointmentId){
//        Appointment findAppointment = appointmentService.findAppointmentById(appointmentId);
//        return appointmentMapper.toResponseDto(findAppointment);
        return null;
    }

    @PostMapping("/api/appointment")
    public SaveAppointmentResponseDto saveAppointment(@RequestBody AppointmentRequestDto appointmentDto, @RequestBody String participantName){
//        Appointment savedAppointment = appointmentService.saveAppointment(appointmentMapper.requestDtoToEntity(appointmentDto));
//        Participant participant = new Participant(null, savedAppointment, participantName);
//        Participant addedParticipant = participantService.addParticipantOfAppointment(participant);
//        return new SaveAppointmentResponseDto(appointmentMapper.toResponseDto(savedAppointment), addedParticipant.getParticipantName());
        return null;
    }
}