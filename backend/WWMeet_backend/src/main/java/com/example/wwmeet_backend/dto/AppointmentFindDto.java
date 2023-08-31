package com.example.wwmeet_backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentFindDto {
    private String appointmentName;
    private String appointmentPlace;
    private String roomCode;
    private int peopleNum;
    private LocalDateTime appointmentDate;
}
