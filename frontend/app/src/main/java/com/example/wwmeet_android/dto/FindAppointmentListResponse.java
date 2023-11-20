package com.example.wwmeet_android.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class FindAppointmentListResponse {
    @SerializedName("id")
    private Long id;
    @SerializedName("appointment_name")
    private String appointmentName;

    @SerializedName("vote_deadline")
    private LocalDateTime deadline;

    @Expose(serialize = false, deserialize = false)
    private boolean isFinishVote;


    public FindAppointmentListResponse(Long id, String appointmentName, LocalDateTime deadline) {
        this.id = id;
        this.appointmentName = appointmentName;
        this.deadline = deadline;
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

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public boolean isFinishVote() {
        return isFinishVote;
    }

    public void setFinishVote(boolean finishVote) {
        isFinishVote = finishVote;
    }
}
