package com.example.wwmeet_backend.participant.domain;

import com.example.wwmeet_backend.appointment.domain.Appointment;
import lombok.*;

import jakarta.persistence.*;
@Entity
@Getter

public class Participant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    private String participantName;

    protected Participant() {
    }

    private Participant (Appointment appointment, String participantName){
        this.appointment = appointment;
        this.participantName = participantName;
    }

    public static Participant of(Appointment appointment, String participantName) {
        return new Participant(appointment, participantName);
    }

}