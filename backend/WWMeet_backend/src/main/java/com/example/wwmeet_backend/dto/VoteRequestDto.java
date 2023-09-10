package com.example.wwmeet_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
public class VoteRequestDto {
    private Long appointmentId;
    private Map<LocalDateTime, LocalDateTime> appointmentSchedule;
}
