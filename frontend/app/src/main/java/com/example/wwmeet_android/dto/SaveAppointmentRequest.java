package com.example.wwmeet_android.dto;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SaveAppointmentRequest implements Serializable {
    private String appointmentName;

    private String appointmentPlace;

    private int participantNum;

    private String participantName;

    private String voteDeadline;

    public SaveAppointmentRequest(String appointmentName, String appointmentPlace, int participantNum, String participantName) {
        this.appointmentName = appointmentName;
        this.appointmentPlace = appointmentPlace;
        this.participantNum = participantNum;
        this.participantName = participantName;
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

    public String getVoteDeadline() {
        return voteDeadline;
    }

    public void setVoteDeadline(String voteDeadline) {
        this.voteDeadline = voteDeadline;
    }

}
