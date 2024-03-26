package com.example.wwmeet_backend.domain.possibleschedule.entity;

import com.example.wwmeet_backend.domain.appointment.entity.Appointment;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;


@Entity
public class PossibleSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    protected PossibleSchedule() {
    }

    private PossibleSchedule(Long id, Appointment appointment, LocalDateTime startTime,
        LocalDateTime endTime) {
        this.id = id;
        this.appointment = appointment;
        this.startTime = LocalDateTime.of(startTime.getYear(), startTime.getMonth(),
            startTime.getDayOfMonth(), startTime.getHour(), 0);
        this.endTime = LocalDateTime.of(endTime.getYear(), endTime.getMonth(),
            endTime.getDayOfMonth(), endTime.getHour(), 0);
    }

    public static PossibleSchedule of(Long id, Appointment appointment, LocalDateTime startTime,
        LocalDateTime endTime) {
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