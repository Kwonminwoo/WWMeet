package com.example.wwmeet_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveAppointmentResponseDto {
    private AppointmentResponseDto appointmentRequestDto;
    private String participantName;
}
