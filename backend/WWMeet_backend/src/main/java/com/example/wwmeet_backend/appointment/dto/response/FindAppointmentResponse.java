package com.example.wwmeet_backend.appointment.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class FindAppointmentResponse {
    private String appointmentName;
    private String appointmentPlace;
    private String identificationCode;
    private int participantNum;
    private LocalDateTime voteDeadline;
    private boolean voteFinish;

    @Builder
    public FindAppointmentResponse(String appointmentName, String appointmentPlace,
        String identificationCode, int participantNum, LocalDateTime voteDeadline,
        boolean voteFinish) {
        this.appointmentName = appointmentName;
        this.appointmentPlace = appointmentPlace;
        this.identificationCode = identificationCode;
        this.participantNum = participantNum;
        this.voteDeadline = voteDeadline;
        this.voteFinish = voteFinish;
    }
}
