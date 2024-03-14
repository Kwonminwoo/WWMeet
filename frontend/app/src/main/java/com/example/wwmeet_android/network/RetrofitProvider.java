package com.example.wwmeet_android.network;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitProvider {
    protected Gson gson;
    protected RetrofitService retrofitService;

    protected final String BASE_URL = "http://10.0.2.2:8080";
//    protected final String BASE_URL = "http://10.0.2.1:8080";
//    protected final String BASE_URL = "http://10.2.19.84:8080";

    public RetrofitProvider() {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        retrofitService = retrofit.create(RetrofitService.class);
    }

    public RetrofitService getService(){
        return retrofitService;
    }

}
