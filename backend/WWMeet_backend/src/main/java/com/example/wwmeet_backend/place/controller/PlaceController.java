package com.example.wwmeet_backend.place.controller;

import com.example.wwmeet_backend.place.dto.request.SavePlaceRequest;
import com.example.wwmeet_backend.place.dto.response.FindAllPlaceResponse;
import com.example.wwmeet_backend.place.service.PlaceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/address")
public class PlaceController {
    private final PlaceService placeService;

    @PostMapping
    public void saveAddress(@RequestBody SavePlaceRequest savePlaceRequest){
        placeService.saveAddress(savePlaceRequest);
    }

    @GetMapping("/{appointment_id}")
    public List<FindAllPlaceResponse> findAllAddress(@PathVariable(name = "appointment_id") Long appointmentId){
        return placeService.findAllAddress(appointmentId);
    }
}
