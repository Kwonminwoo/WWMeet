package com.example.wwmeet_android.dto;

public class ScheduleResponse {
    private String startDateTime;
    private String endTime;

    public ScheduleResponse(String startDateTime, String endTime) {
        this.startDateTime = startDateTime;
        this.endTime = endTime;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endDateTime) {
        this.endTime = endDateTime;
    }
}
