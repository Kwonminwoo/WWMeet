package com.example.wwmeet_backend.service;


import com.example.wwmeet_backend.domain.Appointment;
import com.example.wwmeet_backend.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public List<Appointment> findAllAppointment() {
        return null;
    }
}
