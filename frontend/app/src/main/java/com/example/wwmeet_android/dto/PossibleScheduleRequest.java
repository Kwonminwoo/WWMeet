package com.example.wwmeet_android.dto;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDateTime;

public class PossibleScheduleRequest {
    private String startDateTime;

    private String endDateTime;

    public PossibleScheduleRequest(String startDateTime, String endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public PossibleScheduleRequest() {
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }
}
