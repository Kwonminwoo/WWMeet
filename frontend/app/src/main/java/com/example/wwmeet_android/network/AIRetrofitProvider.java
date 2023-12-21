package com.example.wwmeet_android.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AIRetrofitProvider {
    private Gson gson;
    private RetrofitAIService retrofitAIService;

    private final String BASE_URL = "http://10.2.3.128:8081";

//    private final String BASE_URL = "http://10.2.19.84:8081";

    public AIRetrofitProvider() {
        gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        retrofitAIService = retrofit.create(RetrofitAIService.class);
    }

    public RetrofitAIService getService(){
        return retrofitAIService;
    }

}
