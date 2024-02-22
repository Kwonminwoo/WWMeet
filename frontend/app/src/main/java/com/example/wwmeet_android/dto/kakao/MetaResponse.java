package com.example.wwmeet_android.dto.kakao;

import com.google.gson.annotations.SerializedName;

public class MetaResponse {
    private SameName sameName;
    @SerializedName("pageable_count")
    private String pageableCount;
    @SerializedName("total_count")
    private String totalCount;
    @SerializedName("is_end")
    private boolean isEnd;

    public MetaResponse() {
    }

    public MetaResponse(SameName sameName, String pageableCount, String totalCount, boolean isEnd) {
        this.sameName = sameName;
        this.pageableCount = pageableCount;
        this.totalCount = totalCount;
        this.isEnd = isEnd;
    }
}
