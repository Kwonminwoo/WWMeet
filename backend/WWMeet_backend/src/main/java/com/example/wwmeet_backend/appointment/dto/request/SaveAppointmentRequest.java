package com.example.wwmeet_backend.appointment.dto.request;


import com.example.wwmeet_backend.appointment.domain.Appointment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class SaveAppointmentRequest {
    private String appointmentName;
    private String appointmentPlace;
    private int participantNum;
    private String participantName;
    private LocalDateTime appointmentDeadline;

    public Appointment toEntity(){
        String appointmentCode = createIdentificationCode();
        return Appointment.of(null, appointmentName, appointmentPlace, appointmentCode, participantNum, appointmentDeadline);
    }

    private String createIdentificationCode(){
        UUID identificationCodeUUID = UUID.randomUUID();
        return identificationCodeUUID.toString();
    }
}
