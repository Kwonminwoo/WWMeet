package com.example.wwmeet_backend.appointment.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FindAppointmentListResponse {
    private Long id;
    private String appointmentName;
    private LocalDateTime voteDeadline;

    public FindAppointmentListResponse(Long id) {
        this.id = id;
    }

    @Builder
    public FindAppointmentListResponse(Long id, String appointmentName, LocalDateTime voteDeadline) {
        this.id = id;
        this.appointmentName = appointmentName;
        this.voteDeadline = voteDeadline;
    }

}
