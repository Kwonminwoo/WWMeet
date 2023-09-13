package com.example.wwmeet_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteResponseDto {
    private Long appointmentId;
    private Long participantId;
    private List<PossibleScheduleResponseDto> possibleScheduleList;
}