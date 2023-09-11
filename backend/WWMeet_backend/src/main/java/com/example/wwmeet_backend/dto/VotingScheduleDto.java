package com.example.wwmeet_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class VotingScheduleDto {
    private Long appointmentId;
    private Long participantId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}