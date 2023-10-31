package com.example.wwmeet_backend.appointmentDate.domain;

import com.example.wwmeet_backend.appointment.domain.Appointment;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AppointmentDate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

}
