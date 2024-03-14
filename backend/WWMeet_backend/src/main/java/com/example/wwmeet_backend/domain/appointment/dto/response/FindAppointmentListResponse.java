package com.example.wwmeet_backend.domain.appointment.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindAppointmentListResponse {

    private Long id;
    private String appointmentName;
    private LocalDateTime voteDeadline;
    private boolean voteFinish;
    private String appointmentDate;

    @Builder
    public FindAppointmentListResponse(Long id, String appointmentName, LocalDateTime voteDeadline,
        boolean voteFinish, String appointmentDate) {
        this.id = id;
        this.appointmentName = appointmentName;
        this.voteDeadline = voteDeadline;
        this.voteFinish = voteFinish;
        this.appointmentDate = appointmentDate;
    }
}
