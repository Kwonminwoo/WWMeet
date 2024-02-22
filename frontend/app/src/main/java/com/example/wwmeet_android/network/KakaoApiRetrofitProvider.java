package com.example.wwmeet_android.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KakaoApiRetrofitProvider {
    private Gson gson;
    private KakaoService kakaoService;

    private final String BASE_URL = "https://dapi.kakao.com";
    public KakaoApiRetrofitProvider() {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        kakaoService = retrofit.create(KakaoService.class);
    }


    public KakaoService getService(){
        return kakaoService;
    }
}
