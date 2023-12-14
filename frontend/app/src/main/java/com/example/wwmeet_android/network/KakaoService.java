package com.example.wwmeet_android.network;

import com.example.wwmeet_android.dto.kakao.KakoSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface KakaoService {
    @GET("/v2/local/search/keyword")
    Call<KakoSearchResponse> searchRestaurant(@Header("Authorization") String authorization, @Query("query") String query);
}
