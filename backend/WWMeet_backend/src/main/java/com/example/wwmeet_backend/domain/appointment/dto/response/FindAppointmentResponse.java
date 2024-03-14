package com.example.wwmeet_backend.domain.appointment.dto.response;


import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FindAppointmentResponse {

    private final String appointmentName;
    private final String appointmentPlace;
    private final String identificationCode;
    private final int participantNum;
    private final LocalDateTime voteDeadline;
    private final boolean voteFinish;

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
