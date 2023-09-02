package com.example.wwmeet_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AppointmentFindDto {
    private Long id;
    private String appointmentName;
    private String appointmentPlace;
    private String appointmentCode;
    private int peopleNum;
    private LocalDateTime appointmentDate;
}
