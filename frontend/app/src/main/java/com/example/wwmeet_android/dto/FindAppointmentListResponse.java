package com.example.wwmeet_android.dto;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;

public class FindAppointmentListResponse {
    private Long id;
    private String appointmentName;
    private String voteDeadline;

    @Expose(serialize = false, deserialize = false)
    private boolean isFinishVote;

    @Expose(serialize = false, deserialize = false)
    private String participantName;

    @Expose(serialize = false, deserialize = false)
    private LocalDateTime appointmentDate;


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

    public boolean isFinishVote() {
        return isFinishVote;
    }

    public void setFinishVote(boolean finishVote) {
        isFinishVote = finishVote;
    }

    public void setName(String name){
        this.participantName = name;
    }

    public String getName(){
        return participantName;
    }

    public LocalDateTime getAppointmentDate(){
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate){
        this.appointmentDate = appointmentDate;
    }
}
