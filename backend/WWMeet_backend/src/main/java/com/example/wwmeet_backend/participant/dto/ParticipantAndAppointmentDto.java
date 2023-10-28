package com.example.wwmeet_backend.participant.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantAndAppointmentDto {
    private String appointmentIdentificationCode;
    private String participantName;
}