package com.example.wwmeet_backend.address.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FindAllAddressResponse {
    private String address;
    private double latitude;
    private double longitude;

}
