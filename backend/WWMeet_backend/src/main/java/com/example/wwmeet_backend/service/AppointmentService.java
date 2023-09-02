package com.example.wwmeet_backend.service;


import com.example.wwmeet_backend.domain.Appointment;
import com.example.wwmeet_backend.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    public List<Appointment> findAllAppointment(List<String> appointmentCodeList) {
        List<Appointment> findAppointmentList = new ArrayList<>();
        for (String appointmentCode : appointmentCodeList) {
            Optional<Appointment> findAppointmnetOptional = appointmentRepository.findByAppointmentCode(appointmentCode);
            Appointment findAppointment = findAppointmnetOptional
                    .orElseThrow(() -> new NoSuchElementException());
            findAppointmentList.add(findAppointment);
        }
        return findAppointmentList;
    }

    public Appointment findAppointmentById(Long id){
        Optional<Appointment> findAppointmentOptional = appointmentRepository.findById(id);
        return findAppointmentOptional
                .orElseThrow(() -> new NoSuchElementException());
    }
}
