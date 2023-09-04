package com.example.wwmeet_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AppointmentResponseDto {
    private Long id;
    private String appointmentName;
    private String appointmentPlace;
    private String appointmentCode;
    private int peopleNum;
    private LocalDateTime appointmentDate;
}
