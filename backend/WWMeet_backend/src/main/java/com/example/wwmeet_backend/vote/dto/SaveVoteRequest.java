package com.example.wwmeet_backend.vote.dto;


import com.example.wwmeet_backend.possibleschedule.domain.PossibleSchedule;
import com.example.wwmeet_backend.possibleschedule.dto.SavePossibleScheduleRequest;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveVoteRequest {
    private String participantName;
    private List<SavePossibleScheduleRequest> possibleScheduleList = new ArrayList<>();


    public void addPossibleSchedule(PossibleSchedule possibleSchedule){
        possibleScheduleList.add(new SavePossibleScheduleRequest(possibleSchedule.getStartTime(), possibleSchedule.getEndTime()));
    }

}
