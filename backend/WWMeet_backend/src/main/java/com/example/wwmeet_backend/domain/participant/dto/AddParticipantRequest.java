package com.example.wwmeet_backend.domain.participant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddParticipantRequest {

    private String participantName;
    private String identificationCode;
}
