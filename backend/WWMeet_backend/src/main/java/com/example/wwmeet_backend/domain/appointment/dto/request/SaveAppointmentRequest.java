package com.example.wwmeet_backend.domain.appointment.dto.request;


import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaveAppointmentRequest {

    private String appointmentName;
    private String appointmentPlace;
    private int participantNum;
    private String participantName;
    private LocalDateTime voteDeadline;

    @Builder
    public SaveAppointmentRequest(String appointmentName, String appointmentPlace,
        int participantNum,
        String participantName, LocalDateTime voteDeadline) {
        this.appointmentName = appointmentName;
        this.appointmentPlace = appointmentPlace;
        this.participantNum = participantNum;
        this.participantName = participantName;
        this.voteDeadline = voteDeadline;
    }
}
