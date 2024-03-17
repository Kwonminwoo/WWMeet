package com.example.wwmeet_android.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthRetrofitProvider extends RetrofitProvider{

    public AuthRetrofitProvider(String token) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new TokenInterceptor(token));
        client.followRedirects(false).followSslRedirects(false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client.build())
                .build();

        retrofitService = retrofit.create(RetrofitService.class);
    }

    public RetrofitService getService(){
        return retrofitService;
    }

}
