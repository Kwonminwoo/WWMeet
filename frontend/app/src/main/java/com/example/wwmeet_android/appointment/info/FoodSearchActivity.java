package com.example.wwmeet_android.appointment.info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wwmeet_android.R;
import com.example.wwmeet_android.domain.Restaurant;
import com.example.wwmeet_android.dto.kakao.KakoSearchResponse;
import com.example.wwmeet_android.dto.kakao.SearchRestaurantResponse;
import com.example.wwmeet_android.network.KakaoApiRetrofitProvider;
import com.example.wwmeet_android.network.KakaoService;
import com.example.wwmeet_android.util.KaKaoAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodSearchActivity extends AppCompatActivity {
    private RecyclerView foodRecyclerView;
    private TextView placeNameText;
    private KakaoService kakaoService;
    private String address;
    private String foodName;
    private List<Restaurant> restaurantList = new ArrayList<>();
    private RestaurantListAdapter restaurantListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search);

        init();

        searchRestaurantFromKakao();

    }

    private void init(){
        foodRecyclerView = findViewById(R.id.food_recyclerview);
        placeNameText = findViewById(R.id.food_place);
        restaurantListAdapter = new RestaurantListAdapter();

        KakaoApiRetrofitProvider kakaoApiRetrofitProvider = new KakaoApiRetrofitProvider();
        kakaoService = kakaoApiRetrofitProvider.getService();
    }

    private void searchRestaurantFromKakao(){
        String categoryCode = "FD6";
        Call<KakoSearchResponse> searchRestaurantCall = kakaoService.searchRestaurant(KaKaoAPI.KAKAO_AK, "강남역 카레");
        searchRestaurantCall.enqueue(new Callback<KakoSearchResponse>() {
            @Override
            public void onResponse(Call<KakoSearchResponse> call, Response<KakoSearchResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(FoodSearchActivity.this, "검색에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    try {
                        Log.e("검색 실패", response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }

                KakoSearchResponse searchResponse = response.body();
                List<SearchRestaurantResponse> documents = searchResponse.getDocuments();
                for(SearchRestaurantResponse res: documents){
                    Log.e("url", res.getPlaceUrl());
                }

            }

            @Override
            public void onFailure(Call<KakoSearchResponse> call, Throwable t) {
                Toast.makeText(FoodSearchActivity.this, "서버 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                Log.e("서버 연결 실패", t.getMessage());
            }
        });
    }

    private void crawlingRestaurants(List<SearchRestaurantResponse> searchResultList){

    }

    private void setRestaurantList() {
        restaurantListAdapter.setRestaurantList(restaurantList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        foodRecyclerView.setLayoutManager(linearLayoutManager);
        foodRecyclerView.setAdapter(restaurantListAdapter);
    }
}