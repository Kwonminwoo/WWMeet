package com.example.wwmeet_android.appointment.info.restaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wwmeet_android.R;
import com.example.wwmeet_android.domain.Restaurant;
import com.example.wwmeet_android.dto.FindAllAddressResponse;
import com.example.wwmeet_android.dto.kakao.KakoSearchResponse;
import com.example.wwmeet_android.dto.kakao.SearchRestaurantResponse;
import com.example.wwmeet_android.network.KakaoApiRetrofitProvider;
import com.example.wwmeet_android.network.KakaoService;
import com.example.wwmeet_android.network.ResponseAPI;
import com.example.wwmeet_android.network.RetrofitProvider;
import com.example.wwmeet_android.network.RetrofitService;
import com.example.wwmeet_android.util.KaKaoAPI;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodSearchActivity extends AppCompatActivity {
    private RecyclerView foodRecyclerView;
    private TextView placeNameText;
    private KakaoService kakaoService;
    private RetrofitService retrofitService;
    private String address;
    private String foodName;
    private List<Restaurant> restaurantList = new ArrayList<>();
    private List<Restaurant> restaurantListFromServer = new ArrayList<>();
    private List<String> restaurantUrlList = new ArrayList<>();
    private RestaurantListAdapter restaurantListAdapter;

    private ShimmerFrameLayout skeletonFrame;
    private LinearLayout restaurantListBox;
    private Thread apiThread, placeThread;
    private int page = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search);
        init();

        findAddressCenter();
        getRestaurantsData();
    }

    private void init(){
        foodRecyclerView = findViewById(R.id.food_recyclerview);
        placeNameText = findViewById(R.id.food_place);
        restaurantListAdapter = new RestaurantListAdapter();
        skeletonFrame = findViewById(R.id.skeleton_restaurant);
        restaurantListBox = findViewById(R.id.restart_search_list);

        KakaoApiRetrofitProvider kakaoApiRetrofitProvider = new KakaoApiRetrofitProvider();
        kakaoService = kakaoApiRetrofitProvider.getService();

        RetrofitProvider retrofitProvider = new RetrofitProvider();
        retrofitService = retrofitProvider.getService();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        foodRecyclerView.setLayoutManager(linearLayoutManager);
        foodRecyclerView.setAdapter(restaurantListAdapter);
    }

    private void getRestaurantsData(){
        placeThread.start();
        try {
            placeThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String searchQuery = placeNameText.getText() + " " + getIntent().getStringExtra("food") + " 맛집";
        Toast.makeText(this, searchQuery, Toast.LENGTH_SHORT).show();
        apiThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Call<KakoSearchResponse> searchRestaurantCall = kakaoService.searchRestaurant(KaKaoAPI.KAKAO_AK, searchQuery, 5, page++);
                try {
                    Response<KakoSearchResponse> response = searchRestaurantCall.execute();
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
                    List<Restaurant> resultList = new ArrayList<>();
                    restaurantUrlList.clear();
                    for (SearchRestaurantResponse searchRestaurant : documents) {
                        Restaurant restaurant = new Restaurant();
                        restaurant.setRestaurantName(searchRestaurant.getPlaceName());
                        restaurant.setAddress(searchRestaurant.getRoadAddressName());
                        restaurant.setPhone(searchRestaurant.getPhone());
                        String[] categoryArray = searchRestaurant.getCategoryName().split(" > ");
                        restaurant.setMenu(categoryArray[categoryArray.length - 1]);
                        String httpsUrl = searchRestaurant.getPlaceUrl().replace("http", "https");
                        restaurant.setUrl(httpsUrl);

                        restaurantUrlList.add(httpsUrl);

                        resultList.add(restaurant);
                    }

                    restaurantListFromServer = resultList;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        apiThread.start();

        try {
            apiThread.join();
            getCrawlingRestaurantImage(restaurantUrlList);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCrawlingRestaurantImage(List<String> urlList) {

        Call<ResponseAPI<List<String>>> getImageUrlListCall = retrofitService.getRestaurantImageList(urlList);
        getImageUrlListCall.enqueue(new Callback<ResponseAPI<List<String>>>() {
            @Override
            public void onResponse(Call<ResponseAPI<List<String>>> call, Response<ResponseAPI<List<String>>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(FoodSearchActivity.this, "이미지 조회에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    try {
                        Log.e("이미지 조회 실패", response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }

                for (int i = 0;i < restaurantListFromServer.size();i++) {
                    Restaurant restaurant = restaurantListFromServer.get(i);
                    List<String> imageUrlList = response.body().getData();
                    restaurant.setImageUrl(imageUrlList.get(i));
                }

                setRestaurantList();
                showList();
                loadMoreData();
            }

            @Override
            public void onFailure(Call<ResponseAPI<List<String>>> call, Throwable t) {
                Toast.makeText(FoodSearchActivity.this, "서버 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                Log.e("서버 연결에 실패", t.getMessage());
            }
        });
    }

    private void setRestaurantList() {
        for (Restaurant restaurant : restaurantListFromServer) {
            restaurantListAdapter.addRestaurant(restaurant);
        }
        restaurantListAdapter.notifyDataSetChanged();
    }

    private void showList(){
        skeletonFrame.setVisibility(View.GONE);
        restaurantListBox.setVisibility(View.VISIBLE);
    }

    private void loadMoreData(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getRestaurantsData();
            }
        }, 30000);
    }

    private void findAddressCenter(){
        placeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Call<ResponseAPI<List<FindAllAddressResponse>>> findAddressCall = retrofitService.findAllAddress(getIntent().getLongExtra("appointmentId", -1));
                try {
                    Response<ResponseAPI<List<FindAllAddressResponse>>> response = findAddressCall.execute();
                    if (!response.isSuccessful()) {
                        Toast.makeText(FoodSearchActivity.this, "주소 조회에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        try {
                            Log.e("주소 조회 실패", response.errorBody().string());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return;
                    }
                    List<FindAllAddressResponse> addressList = response.body().getData();
                    double latitudeSum = 0.0;
                    double longitudeSum = 0.0;
                    for (FindAllAddressResponse address : addressList) {
                        latitudeSum += address.getLatitude();
                        longitudeSum += address.getLongitude();
                    }

                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.KOREA);
                    try {
                        List<Address> fromLocation = geocoder.getFromLocation(latitudeSum / addressList.size(), longitudeSum / addressList.size(), 1);
                        String addressName = fromLocation.get(0).getThoroughfare();

                        placeNameText.setText(addressName);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}
