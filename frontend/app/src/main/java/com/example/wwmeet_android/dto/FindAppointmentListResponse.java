package com.example.wwmeet_android.dto;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;

public class FindAppointmentListResponse {
    private Long id;
    private String appointmentName;
    private String voteDeadline;
    private boolean voteFinish;
    private String appointmentDate;

    private String participantName;


    public FindAppointmentListResponse(Long id, String appointmentName, String voteDeadline) {
        this.id = id;
        this.appointmentName = appointmentName;
        this.voteDeadline = voteDeadline;
    }

    public FindAppointmentListResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppointmentName() {
        return appointmentName;
    }

    public void setAppointmentName(String appointmentName) {
        this.appointmentName = appointmentName;
    }

    public String getVoteDeadline() {
        return voteDeadline;
    }

    public void setVoteDeadline(String voteDeadline) {
        this.voteDeadline = voteDeadline;
    }

    public String getAppointmentDate(){
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate){
        this.appointmentDate = appointmentDate;
    }

    public boolean isVoteFinish() {
        return voteFinish;
    }

    public void setVoteFinish(boolean voteFinish) {
        this.voteFinish = voteFinish;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }
}
