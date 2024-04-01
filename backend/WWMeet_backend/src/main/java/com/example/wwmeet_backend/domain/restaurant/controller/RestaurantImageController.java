package com.example.wwmeet_backend.domain.restaurant.controller;


import com.example.wwmeet_backend.domain.restaurant.service.RestaurantCrawlingService;
import com.example.wwmeet_backend.global.response.ResponseAPI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestaurantImageController {

    private final RestaurantCrawlingService restaurantCrawlingService;

    @GetMapping("/api/restaurants/images")
    public ResponseEntity<ResponseAPI> getRestaurantImageUrl(@RequestParam List<String> urlList) {
        return ResponseEntity.ok(ResponseAPI.response("음식점 사진 조회 성공",
            restaurantCrawlingService.getRestaurantImageList(urlList)));
    }

}
