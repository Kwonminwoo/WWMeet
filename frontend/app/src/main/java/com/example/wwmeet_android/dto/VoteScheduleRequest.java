package com.example.wwmeet_android.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VoteScheduleRequest {

    private String participantName;

    private List<PossibleScheduleRequest> possibleScheduleList;

    public VoteScheduleRequest(String participantName, List<PossibleScheduleRequest> possibleScheduleList) {
        this.participantName = participantName;
        this.possibleScheduleList = possibleScheduleList;
    }

    public VoteScheduleRequest() {
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public List<PossibleScheduleRequest> getPossibleScheduleList() {
        return possibleScheduleList;
    }

    public void setPossibleScheduleRequestList(List<PossibleScheduleRequest> possibleScheduleList) {
        this.possibleScheduleList = possibleScheduleList;
    }
}
