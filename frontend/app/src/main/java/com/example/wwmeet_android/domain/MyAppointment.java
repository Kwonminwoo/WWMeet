package com.example.wwmeet_android.domain;

public class MyAppointment {
    private Long appointmentId;
    private String name;

    public MyAppointment(Long appointmentId, String name) {
        this.appointmentId = appointmentId;
        this.name = name;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
