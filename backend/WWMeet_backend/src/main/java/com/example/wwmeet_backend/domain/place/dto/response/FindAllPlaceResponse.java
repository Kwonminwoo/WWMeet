package com.example.wwmeet_backend.domain.place.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FindAllPlaceResponse {

    private String participantName;
    private String address;
    private double latitude;
    private double longitude;

}
