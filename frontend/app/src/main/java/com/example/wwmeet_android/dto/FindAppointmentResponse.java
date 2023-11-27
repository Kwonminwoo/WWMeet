package com.example.wwmeet_android.dto;

import java.time.LocalDateTime;

public class FindAppointmentResponse {
    private String appointmentName;

    private String appointmentPlace;

    private String identificationCode;

    private int participantNum;

    private LocalDateTime voteDeadline;

    public FindAppointmentResponse(String appointmentNAme, String appointmentPlace, String identificationCode, int participantNum, LocalDateTime voteDeadline) {
        this.appointmentName = appointmentNAme;
        this.appointmentPlace = appointmentPlace;
        this.identificationCode = identificationCode;
        this.participantNum = participantNum;
        this.voteDeadline = voteDeadline;
    }

    public FindAppointmentResponse() {
    }

    public String getAppointmentName() {
        return appointmentName;
    }

    public void setAppointmentName(String appointmentName) {
        this.appointmentName = appointmentName;
    }

    public String getAppointmentPlace() {
        return appointmentPlace;
    }

    public void setAppointmentPlace(String appointmentPlace) {
        this.appointmentPlace = appointmentPlace;
    }

    public String getIdentificationCode() {
        return identificationCode;
    }

    public void setIdentificationCode(String identificationCode) {
        this.identificationCode = identificationCode;
    }

    public int getParticipantNum() {
        return participantNum;
    }

    public void setParticipantNum(int participantNum) {
        this.participantNum = participantNum;
    }

    public LocalDateTime getDeadline() {
        return voteDeadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.voteDeadline = deadline;
    }
}
