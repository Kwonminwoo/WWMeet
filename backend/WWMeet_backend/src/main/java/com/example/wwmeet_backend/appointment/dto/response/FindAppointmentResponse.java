package com.example.wwmeet_backend.appointment.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
public class FindAppointmentResponse {
    private String appointmentName;
    private String appointmentPlace;
    private String identificationCode;
    private int participantNum;
    private LocalDateTime voteDeadline;
}
