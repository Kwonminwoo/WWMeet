package com.example.wwmeet_backend.domain.place.controller;

import com.example.wwmeet_backend.domain.place.dto.request.SavePlaceRequest;
import com.example.wwmeet_backend.domain.place.dto.response.FindAllPlaceResponse;
import com.example.wwmeet_backend.domain.place.service.PlaceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> saveAddress(@RequestBody SavePlaceRequest savePlaceRequest) {
        placeService.saveAddress(savePlaceRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{appointment_id}")
    public ResponseEntity<List<FindAllPlaceResponse>> findAllAddress(
        @PathVariable(name = "appointment_id") Long appointmentId) {
        return ResponseEntity.ok(placeService.findAllAddress(appointmentId));
    }
}
