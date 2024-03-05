package com.example.wwmeet_backend.place.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SavePlaceRequest {
    private Long appointmentId;
    private String participantName;
    private String address;
    private double latitude;
    private double longitude;
}
