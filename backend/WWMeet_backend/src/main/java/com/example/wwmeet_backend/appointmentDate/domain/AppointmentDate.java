package com.example.wwmeet_backend.appointmentDate.domain;

import com.example.wwmeet_backend.appointment.domain.Appointment;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Builder;

@Entity
public class AppointmentDate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @Builder
    private AppointmentDate(LocalDateTime startDate, LocalDateTime endDate,
        Appointment appointment) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.appointment = appointment;
    }
}
