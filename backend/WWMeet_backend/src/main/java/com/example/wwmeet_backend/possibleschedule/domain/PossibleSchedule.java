package com.example.wwmeet_backend.possibleschedule.domain;

import com.example.wwmeet_backend.appointment.domain.Appointment;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
public class PossibleSchedule {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    protected PossibleSchedule() {
    }

    private PossibleSchedule(Long id, Appointment appointment, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.appointment = appointment;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static PossibleSchedule of(Long id, Appointment appointment, LocalDateTime startTime, LocalDateTime endTime) {
        return new PossibleSchedule(id, appointment, startTime, endTime);
    }

    public Long getId() {
        return id;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}