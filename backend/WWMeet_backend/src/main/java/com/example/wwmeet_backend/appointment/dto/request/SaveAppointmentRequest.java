package com.example.wwmeet_backend.appointment.dto.request;


import com.example.wwmeet_backend.appointment.domain.Appointment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SaveAppointmentRequest {
    private String appointmentName;
    private String appointmentPlace;
    private int participantNum;
    private String participantName;
    private LocalDateTime voteDeadline;

    public SaveAppointmentRequest() {
    }

    public Appointment toEntity(){
        String appointmentCode = createIdentificationCode();
        return Appointment.of(null, appointmentName, appointmentPlace, appointmentCode, participantNum, voteDeadline);
    }

    private String createIdentificationCode(){
        UUID identificationCodeUUID = UUID.randomUUID();
        return identificationCodeUUID.toString();
    }
}
