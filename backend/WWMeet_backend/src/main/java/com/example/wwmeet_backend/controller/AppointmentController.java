package com.example.wwmeet_backend.controller;


import com.example.wwmeet_backend.domain.Appointment;
import com.example.wwmeet_backend.dto.AppointmentRequestDto;
import com.example.wwmeet_backend.dto.AppointmentResponseDto;
import com.example.wwmeet_backend.mapper.AppointmentMapper;
import com.example.wwmeet_backend.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;
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
    public AppointmentResponseDto saveAppointment(@RequestBody AppointmentRequestDto appointmentDto){
        Appointment savedAppointment = appointmentService.saveAppointment(appointmentMapper.requestDtoToEntity(appointmentDto));
        return appointmentMapper.toResponseDto(savedAppointment);
    }
}