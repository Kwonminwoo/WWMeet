package com.example.wwmeet_backend.domain.vote.dto;


import com.example.wwmeet_backend.domain.possibleschedule.entity.PossibleSchedule;
import com.example.wwmeet_backend.domain.possibleschedule.dto.SavePossibleScheduleRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveVoteRequest {

    private String participantName;
    private List<SavePossibleScheduleRequest> possibleScheduleList = new ArrayList<>();


    public void addPossibleSchedule(PossibleSchedule possibleSchedule) {
        possibleScheduleList.add(new SavePossibleScheduleRequest(possibleSchedule.getStartTime(),
            possibleSchedule.getEndTime()));
    }

}
