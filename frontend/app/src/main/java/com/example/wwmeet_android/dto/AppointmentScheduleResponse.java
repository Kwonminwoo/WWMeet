package com.example.wwmeet_android.dto;

import java.time.LocalDateTime;

public class AppointmentScheduleResponse {
    private ScheduleResponse firstSchedule;
    private ScheduleResponse secondSchedule;
    private ScheduleResponse thirdSchedule;

    public AppointmentScheduleResponse(ScheduleResponse firstSchedule, ScheduleResponse secondSchedule, ScheduleResponse thirdSchedule) {
        this.firstSchedule = firstSchedule;
        this.secondSchedule = secondSchedule;
        this.thirdSchedule = thirdSchedule;
    }

    public ScheduleResponse getFirstSchedule() {
        return firstSchedule;
    }

    public void setFirstSchedule(ScheduleResponse firstSchedule) {
        this.firstSchedule = firstSchedule;
    }

    public ScheduleResponse getSecondSchedule() {
        return secondSchedule;
    }

    public void setSecondSchedule(ScheduleResponse secondSchedule) {
        this.secondSchedule = secondSchedule;
    }

    public ScheduleResponse getThirdSchedule() {
        return thirdSchedule;
    }

    public void setThirdSchedule(ScheduleResponse thirdSchedule) {
        this.thirdSchedule = thirdSchedule;
    }
}
