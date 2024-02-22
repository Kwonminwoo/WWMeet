package com.example.wwmeet_android.dto.kakao;

import com.google.gson.annotations.SerializedName;

public class SearchRestaurantResponse {
    @SerializedName("place_name")
    private String placeName;
    private String distance;
    @SerializedName("place_url")
    private String placeUrl;
    @SerializedName("category_name")
    private String categoryName;

    @SerializedName("address_name")
    private String addressName;

    @SerializedName("road_address_name")
    private String roadAddressName;

    private String id;
    private String phone;
    @SerializedName("category_group_code")
    private String categoryGroupCode;

    @SerializedName("category_group_name")
    private String categoryGroupName;
    private String x;
    private String y;


    public SearchRestaurantResponse(String placeName, String distance, String placeUrl, String categoryName,
                String addressName, String roadAddressName, String id, String phone,
                String categoryGroupCode, String categoryGroupName, String x, String y) {
        this.placeName = placeName;
        this.distance = distance;
        this.placeUrl = placeUrl;
        this.categoryName = categoryName;
        this.addressName = addressName;
        this.roadAddressName = roadAddressName;
        this.id = id;
        this.phone = phone;
        this.categoryGroupCode = categoryGroupCode;
        this.categoryGroupName = categoryGroupName;
        this.x = x;
        this.y = y;
    }

    public SearchRestaurantResponse() {
    }

    public String getPlaceUrl() {
        return placeUrl;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getDistance() {
        return distance;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getAddressName() {
        return addressName;
    }

    public String getRoadAddressName() {
        return roadAddressName;
    }

    public String getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getCategoryGroupCode() {
        return categoryGroupCode;
    }

    public String getCategoryGroupName() {
        return categoryGroupName;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }
}
