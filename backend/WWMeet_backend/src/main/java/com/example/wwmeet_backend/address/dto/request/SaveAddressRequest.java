package com.example.wwmeet_backend.address.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SaveAddressRequest {
    private Long appointmentId;
    private String participantName;
    private String address;
    private double latitude;
    private double longitude;
}
