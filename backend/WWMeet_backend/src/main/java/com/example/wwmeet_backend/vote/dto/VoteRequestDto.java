package com.example.wwmeet_backend.vote.dto;


import com.example.wwmeet_backend.possibleschedule.domain.PossibleSchedule;
import com.example.wwmeet_backend.possibleschedule.dto.PossibleScheduleRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteRequestDto {
    private Long appointmentId;
    private Long participantId;
    private List<PossibleScheduleRequestDto> possibleScheduleList = new ArrayList<>();


    public VoteRequestDto(Long appointmentId, Long participantId) {
        this.appointmentId = appointmentId;
        this.participantId = participantId;
    }

    public void addPossibleSchedule(PossibleSchedule possibleSchedule){
        possibleScheduleList.add(new PossibleScheduleRequestDto(possibleSchedule.getStartTime(), possibleSchedule.getEndTime()));
    }
}
