package com.example.wwmeet_backend.participant.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FindParticipantResponse {
    private String participantName;
    private boolean voteState;
}
