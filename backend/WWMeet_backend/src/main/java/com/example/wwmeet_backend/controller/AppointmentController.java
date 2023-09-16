package com.example.wwmeet_backend.controller;


import com.example.wwmeet_backend.domain.Appointment;
import com.example.wwmeet_backend.domain.Participant;
import com.example.wwmeet_backend.dto.AppointmentRequestDto;
import com.example.wwmeet_backend.dto.AppointmentResponseDto;
import com.example.wwmeet_backend.dto.SaveAppointmentResponseDto;
import com.example.wwmeet_backend.mapper.AppointmentMapper;
import com.example.wwmeet_backend.service.AppointmentService;
import com.example.wwmeet_backend.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;
    private final ParticipantService participantService;
    @GetMapping("/api/appointments")
    public List<AppointmentResponseDto> findAllAppointment(@RequestParam List<String> appointmentCodeList){
        List<Appointment> findAppointmentList = appointmentService.findAllAppointment(appointmentCodeList);
        List<AppointmentResponseDto> findAppointmentDtoList = new ArrayList<>();
        for (Appointment appointment : findAppointmentList) {
            findAppointmentDtoList.add(appointmentMapper.toResponseDto(appointment));
        }
        return findAppointmentDtoList;
    }

    @GetMapping("/api/appointment/{appointment_id}")
    public AppointmentResponseDto findAppointmentById(@PathVariable(name = "appointment_id") Long appointmentId){
        Appointment findAppointment = appointmentService.findAppointmentById(appointmentId);
        return appointmentMapper.toResponseDto(findAppointment);
    }

    @PostMapping("/api/appointment")
    public SaveAppointmentResponseDto saveAppointment(@RequestBody AppointmentRequestDto appointmentDto, @RequestBody String participantName){
        Appointment savedAppointment = appointmentService.saveAppointment(appointmentMapper.requestDtoToEntity(appointmentDto));
        Participant participant = new Participant(null, savedAppointment, participantName);
        Participant addedParticipant = participantService.addParticipantOfAppointment(participant);
        return new SaveAppointmentResponseDto(appointmentMapper.toResponseDto(savedAppointment), addedParticipant.getParticipantName());
    }
}