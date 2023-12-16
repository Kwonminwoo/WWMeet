package com.example.wwmeet_android.domain;

public class Restaurant {
    private String restaurantName;
    private String menu;
    private String imageUrl;
    private String address;
    private String phone;

    public Restaurant(String restaurantName, String menu, String imageUrl, String address, String phone) {
        this.restaurantName = restaurantName;
        this.menu = menu;
        this.imageUrl = imageUrl;
        this.address = address;
        this.phone = phone;
    }

    public Restaurant() {
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
