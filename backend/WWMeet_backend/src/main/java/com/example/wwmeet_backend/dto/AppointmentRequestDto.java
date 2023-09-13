package com.example.wwmeet_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequestDto {
    private Long id;
    private String appointmentName;
    private String appointmentPlace;
    private String identificationCode;
    private int peopleNum;
    private LocalDateTime deadline;
}