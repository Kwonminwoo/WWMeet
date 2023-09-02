package com.example.wwmeet_backend.controller;


import com.example.wwmeet_backend.domain.Appointment;
import com.example.wwmeet_backend.dto.AppointmentFindDto;
import com.example.wwmeet_backend.mapper.AppointmentMapper;
import com.example.wwmeet_backend.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;
    @GetMapping("/api/appointments")
    public List<AppointmentFindDto> findAllAppointment(@RequestParam List<String> appointmentCodeList){
        List<Appointment> findAppointmentList = appointmentService.findAllAppointment(appointmentCodeList);
        List<AppointmentFindDto> findAppointmentDtoList = new ArrayList<>();
        for (Appointment appointment : findAppointmentList) {
            findAppointmentDtoList.add(appointmentMapper.toFindDto(appointment));
        }

        return findAppointmentDtoList;
    }

    @GetMapping("/api/appointment/{appointment_id}")
    public AppointmentFindDto findAppointmentById(@PathVariable(name = "appointment_id") Long appointmentId){
        Appointment findAppointment = appointmentService.findAppointmentById(appointmentId);
        return appointmentMapper.toFindDto(findAppointment);
    }
}