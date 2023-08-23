package com.example.wwmeet_backend.repository;

import com.example.wwmeet_backend.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}