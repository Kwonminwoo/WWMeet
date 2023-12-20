package com.example.wwmeet_android.appointment.info;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wwmeet_android.MainActivity;
import com.example.wwmeet_android.R;
import com.example.wwmeet_android.domain.UserLocation;
import com.example.wwmeet_android.dto.FindAllAddressResponse;
import com.example.wwmeet_android.network.RetrofitProvider;
import com.example.wwmeet_android.network.RetrofitService;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PlaceResultActivity extends AppCompatActivity implements OnMapReadyCallback {
    RecyclerView recyclerView;
    LinearLayout userPlaceListBox;
    Button createBtn;

    UserLocationListAdapter listAdapter = new UserLocationListAdapter();
    List<UserLocation> userLocationList = new ArrayList<>();

    private RetrofitService retrofitService;
    private NaverMap naverMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_result);
        init();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        findAllAddress();

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init(){
        recyclerView = findViewById(R.id.place_result_recyclerview);
        userPlaceListBox = findViewById(R.id.place_result_list_box);

        createBtn = findViewById(R.id.place_result_confirm_btn);
        RetrofitProvider retrofitProvider = new RetrofitProvider();
        retrofitService = retrofitProvider.getService();
    }

    private void findAllAddress(){
        Intent intent = getIntent();
        long appointmentId = intent.getLongExtra("appointmentId", -1);
        Call<List<FindAllAddressResponse>> findAddressCall = retrofitService.findAllAddress(appointmentId);
        findAddressCall.enqueue(new Callback<List<FindAllAddressResponse>>() {
            @Override
            public void onResponse(Call<List<FindAllAddressResponse>> call, Response<List<FindAllAddressResponse>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(PlaceResultActivity.this, "주소 조회 실패했습니다.", Toast.LENGTH_SHORT).show();
                    try {
                        Log.e("주소 조회 실패", response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }

                for(FindAllAddressResponse findAddress : response.body()){
                    userLocationList.add(new UserLocation(findAddress.getParticipantName(),
                            findAddress.getAddress(), findAddress.getLatitude(), findAddress.getLongitude()));
                }

                setParticipantList();
                setMarker();
            }

            @Override
            public void onFailure(Call<List<FindAllAddressResponse>> call, Throwable t) {
                Toast.makeText(PlaceResultActivity.this, "서버 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                Log.e("서버 연결에 실패", t.getMessage());
            }
        });

    }

    private void setParticipantList(){
        listAdapter.setList(userLocationList);
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
    }

    private void setMarker(){
        List<LatLng> latLngList = new ArrayList<>();
        for (int i = 0; i < userLocationList.size(); i++) {
            UserLocation userLocation = userLocationList.get(i);
            Marker marker = new Marker();
            LatLng latLng = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
            latLngList.add(latLng);
            marker.setPosition(latLng);
            marker.setMap(naverMap);
        }

        double latitudeSum = 0;
        double longitudeSum = 0;

        for (LatLng latLng : latLngList) {
            latitudeSum += latLng.latitude;
            longitudeSum += latLng.longitude;
        }

        Marker marker = new Marker();
        LatLng center = new LatLng(latitudeSum / latLngList.size(),
                longitudeSum / latLngList.size());
        
        marker.setPosition(center);
    }

}