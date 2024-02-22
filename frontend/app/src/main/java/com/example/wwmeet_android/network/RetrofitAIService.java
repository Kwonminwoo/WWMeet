package com.example.wwmeet_android.network;

import com.example.wwmeet_android.dto.FindAnalysisResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitAIService {
    @GET("/test2")
    Call<FindAnalysisResponse> findAnalysisById(@Query("sentence") String sentence, @Query("foodtype") String foodtype);

}
