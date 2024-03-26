package com.example.wwmeet_backend.domain.appointmentDate.entity;

import com.example.wwmeet_backend.domain.appointment.entity.Appointment;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class AppointmentDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    protected AppointmentDate() {
    }

    @Builder
    private AppointmentDate(LocalDateTime startDate, LocalDateTime endDate,
        Appointment appointment) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.appointment = appointment;
    }
}
