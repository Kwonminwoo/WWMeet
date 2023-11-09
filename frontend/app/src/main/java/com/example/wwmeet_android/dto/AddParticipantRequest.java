package com.example.wwmeet_android.dto;

import com.google.gson.annotations.SerializedName;

public class AddParticipantRequest {
    @SerializedName("participant_name")
    private String participantName;

    @SerializedName("identification_code")
    private String identificationCode;

    public AddParticipantRequest(String participantName, String identificationCode) {
        this.participantName = participantName;
        this.identificationCode = identificationCode;
    }

    public AddParticipantRequest() {
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getIdentificationCode() {
        return identificationCode;
    }

    public void setIdentificationCode(String identificationCode) {
        this.identificationCode = identificationCode;
    }
}
