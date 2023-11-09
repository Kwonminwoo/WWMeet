package com.example.wwmeet_android.dto;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class SaveAppointmentRequest {
    @SerializedName("appointment_name")
    private String appointmentName;

    @SerializedName("appointment_place")
    private String appointmentPlace;

    @SerializedName("participant_num")
    private int participantNum;

    @SerializedName("participant_name")
    private String participantName;

    @SerializedName("vote_deadline")
    private LocalDateTime deadline;

    public SaveAppointmentRequest(String appointmentName, String appointmentPlace, int participantNum, String participantName, LocalDateTime deadline) {
        this.appointmentName = appointmentName;
        this.appointmentPlace = appointmentPlace;
        this.participantNum = participantNum;
        this.participantName = participantName;
        this.deadline = deadline;
    }

    public SaveAppointmentRequest() {
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

    public int getParticipantNum() {
        return participantNum;
    }

    public void setParticipantNum(int participantNum) {
        this.participantNum = participantNum;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
