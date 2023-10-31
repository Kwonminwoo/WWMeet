package com.example.wwmeet_backend.participant.domain;

import com.example.wwmeet_backend.appointment.domain.Appointment;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter

public class Participant {
    @Id @GeneratedValue
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    private String participantName;

    protected Participant() {
    }

    private Participant (Long id, Appointment appointment, String participantName){
        this.id = id;
        this.appointment = appointment;
        this.participantName = participantName;
    }

    public static Participant of(Long id, Appointment appointment, String participantName) {
        return new Participant(id, appointment, participantName);
    }

}