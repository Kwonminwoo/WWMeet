package com.example.wwmeet_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantResponse {
    private String appointmentIdentificationCode;
    private String participantName;
}