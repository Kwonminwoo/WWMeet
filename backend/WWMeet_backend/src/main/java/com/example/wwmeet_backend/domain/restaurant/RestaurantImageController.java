package com.example.wwmeet_backend.domain.restaurant;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestaurantImageController {

    private final RestaurantCrawlingService restaurantCrawlingService;

    @GetMapping("/api/restaurants/images")
    public List<String> getRestaurantImageUrl(@RequestParam List<String> urlList) {
        return restaurantCrawlingService.getRestaurantImageList(urlList);
    }

}