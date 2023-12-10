package com.example.wwmeet_backend.address.controller;

import com.example.wwmeet_backend.address.dto.request.SaveAddressRequest;
import com.example.wwmeet_backend.address.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/address")
public class AddressController {
    private final AddressService addressService;

    @PostMapping
    public void saveAddress(@RequestBody SaveAddressRequest saveAddressRequest){
        addressService.saveAddress(saveAddressRequest);
    }

}
