package com.example.wwmeet_backend.domain.appointment.dto.request;


import com.example.wwmeet_backend.domain.appointment.domain.Appointment;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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

    public Appointment toEntity() {
        String appointmentCode = createIdentificationCode();
        return Appointment.of(null, appointmentName, appointmentPlace, appointmentCode,
            participantNum, voteDeadline);
    }

    private String createIdentificationCode() {
        UUID identificationCodeUUID = UUID.randomUUID();
        String identificationCode = identificationCodeUUID.toString().substring(0, 15);
        return identificationCode;
    }
}
