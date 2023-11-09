package com.example.wwmeet_android.dto;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class FindAppointmentResponse {
    @SerializedName("appointment_name")
    private String appointmentNAme;

    @SerializedName("appointment_place")
    private String appointmentPlace;

    @SerializedName("identification_code")
    private String identificationCode;

    @SerializedName("participant_num")
    private int participantNum;

    @SerializedName("vote_deadline")
    private LocalDateTime deadline;

    public FindAppointmentResponse(String appointmentNAme, String appointmentPlace, String identificationCode, int participantNum, LocalDateTime deadline) {
        this.appointmentNAme = appointmentNAme;
        this.appointmentPlace = appointmentPlace;
        this.identificationCode = identificationCode;
        this.participantNum = participantNum;
        this.deadline = deadline;
    }

    public FindAppointmentResponse() {
    }

    public String getAppointmentNAme() {
        return appointmentNAme;
    }

    public void setAppointmentNAme(String appointmentNAme) {
        this.appointmentNAme = appointmentNAme;
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
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
