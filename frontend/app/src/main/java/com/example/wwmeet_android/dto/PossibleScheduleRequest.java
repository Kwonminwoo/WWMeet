package com.example.wwmeet_android.dto;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class PossibleScheduleRequest {
    @SerializedName("start_date_time")
    private LocalDateTime startDateTime;

    @SerializedName("end_date_time")
    private LocalDateTime endDateTime;

    public PossibleScheduleRequest(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public PossibleScheduleRequest() {
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }
}
