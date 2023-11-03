package com.example.wwmeet_backend.appointment.domain;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Appointment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "appointment_name")
    private String appointmentName;

    @Column(name = "appointment_place")
    private String appointmentPlace;

    @Column(name = "identification_code")
    private String identificationCode;

    @Column(name = "people_num")
    private int participantNum;
    
    @Column(name = "vote_deadline")
    private LocalDateTime voteDeadline;

    protected Appointment() {
    }

    private Appointment(Long id, String appointmentName, String appointmentPlace, String identificationCode, int participantNum, LocalDateTime voteDeadline) {
        this.id = id;
        this.appointmentName = appointmentName;
        this.appointmentPlace = appointmentPlace;
        this.identificationCode = identificationCode;
        this.participantNum = participantNum;
        this.voteDeadline = voteDeadline;
    }

    public static Appointment of(Long id, String appointmentName, String appointmentPlace, String identificationCode, int participantNum, LocalDateTime voteDeadline){
        return new Appointment(id, appointmentName, appointmentPlace, identificationCode, participantNum, voteDeadline);
    }

    public Long getId() {
        return id;
    }

    public String getAppointmentName() {
        return appointmentName;
    }

    public String getAppointmentPlace() {
        return appointmentPlace;
    }

    public String getIdentificationCode() {
        return identificationCode;
    }

    public int getParticipantNum() {
        return participantNum;
    }

    public LocalDateTime getVoteDeadline() {
        return voteDeadline;
    }
}