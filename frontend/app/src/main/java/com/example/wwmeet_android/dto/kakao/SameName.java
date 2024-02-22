package com.example.wwmeet_android.dto.kakao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SameName {
    private List<String> region;
    private String keyword;
    @SerializedName("selected_region")
    private String selectedRegion;

    public SameName() {
    }

    public SameName(List<String> region, String keyword, String selectedRegion) {
        this.region = region;
        this.keyword = keyword;
        this.selectedRegion = selectedRegion;
    }
}
