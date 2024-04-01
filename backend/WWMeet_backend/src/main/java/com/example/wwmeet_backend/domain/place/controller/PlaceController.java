package com.example.wwmeet_backend.domain.place.controller;

import com.example.wwmeet_backend.domain.place.dto.request.SavePlaceRequest;
import com.example.wwmeet_backend.domain.place.dto.response.FindAllPlaceResponse;
import com.example.wwmeet_backend.domain.place.service.PlaceService;
import com.example.wwmeet_backend.global.response.ResponseAPI;
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
    public ResponseEntity<ResponseAPI> saveAddress(@RequestBody SavePlaceRequest savePlaceRequest) {
        placeService.saveAddress(savePlaceRequest);
        return ResponseEntity.ok(ResponseAPI.response("주소 저장 성공"));
    }

    @GetMapping("/{appointment_id}")
    public ResponseEntity<ResponseAPI> findAllAddress(
        @PathVariable(name = "appointment_id") Long appointmentId) {
        return ResponseEntity.ok(ResponseAPI.response("모든 주소 조회 성공",
            placeService.findAllAddress(appointmentId)));
    }
}
