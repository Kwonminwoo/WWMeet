package com.example.wwmeet_android.dto.kakao;

import java.util.List;

public class KakoSearchResponse {
    private MetaResponse meta;
    private List<SearchRestaurantResponse> documents;

    public KakoSearchResponse() {
    }

    public KakoSearchResponse(MetaResponse meta, List<SearchRestaurantResponse> documents) {
        this.meta = meta;
        this.documents = documents;
    }

    public MetaResponse getMeta() {
        return meta;
    }

    public void setMeta(MetaResponse meta) {
        this.meta = meta;
    }

    public List<SearchRestaurantResponse> getDocuments() {
        return documents;
    }

    public void setDocuments(List<SearchRestaurantResponse> documents) {
        this.documents = documents;
    }
}
