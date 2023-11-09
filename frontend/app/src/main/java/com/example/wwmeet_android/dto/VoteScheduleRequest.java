package com.example.wwmeet_android.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VoteScheduleRequest {

    @SerializedName("participant_name")
    private String participantName;

    @SerializedName("possible_schedule")
    private List<PossibleScheduleRequest> possibleScheduleRequestList;

    public VoteScheduleRequest(String participantName, List<PossibleScheduleRequest> possibleScheduleRequestList) {
        this.participantName = participantName;
        this.possibleScheduleRequestList = possibleScheduleRequestList;
    }

    public VoteScheduleRequest() {
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public List<PossibleScheduleRequest> getPossibleScheduleRequestList() {
        return possibleScheduleRequestList;
    }

    public void setPossibleScheduleRequestList(List<PossibleScheduleRequest> possibleScheduleRequestList) {
        this.possibleScheduleRequestList = possibleScheduleRequestList;
    }
}
