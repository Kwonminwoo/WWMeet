package com.example.wwmeet_backend.appointment.dto.response;

import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class DateRangeResponse {
    private LocalDateTime startDateTime;
    private LocalTime endTime;
}
