package com.example.wwmeet_android.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProvider {
    private Gson gson;
    private RetrofitService retrofitService;

    private final String BASE_URL = "http://10.0.2.2:8080";
//    private final String BASE_URL = "http://10.0.2.1:8080";

//    private final String BASE_URL = "http://10.2.19.84:8080";


    public RetrofitProvider() {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        retrofitService = retrofit.create(RetrofitService.class);
    }


    public RetrofitService getService(){
        return retrofitService;
    }
}
