package com.example.wwmeet_android.dto;


public class SaveAddressRequest {
    private Long appointmentId;
    private String participantName;
    private String address;
    private double latitude;
    private double longitude;

    public SaveAddressRequest(Long appointmentId, String participantName, String address, double latitude, double longitude) {
        this.appointmentId = appointmentId;
        this.participantName = participantName;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
