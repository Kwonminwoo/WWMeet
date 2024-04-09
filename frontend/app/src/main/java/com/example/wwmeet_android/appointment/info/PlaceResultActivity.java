package com.example.wwmeet_android.appointment.info;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wwmeet_android.MainActivity;
import com.example.wwmeet_android.R;
import com.example.wwmeet_android.database.SharedPreferenceUtil;
import com.example.wwmeet_android.domain.UserLocation;
import com.example.wwmeet_android.dto.FindAllAddressResponse;
import com.example.wwmeet_android.network.AuthRetrofitProvider;
import com.example.wwmeet_android.network.ResponseAPI;
import com.example.wwmeet_android.network.RetrofitProvider;
import com.example.wwmeet_android.network.RetrofitService;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    private TextView placeNameText;
    private MapView mapView;

    private NaverMap naverMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;

    private FusedLocationSource locationSource;
    private static final String[] PERMISSION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_result);

        init();

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void init(){
        recyclerView = findViewById(R.id.place_result_recyclerview);
        userPlaceListBox = findViewById(R.id.place_result_list_box);
        mapView = findViewById(R.id.place_result_map_view);
        placeNameText = findViewById(R.id.place_result_name_text);
        placeNameText.setSelected(true);
        createBtn = findViewById(R.id.place_result_confirm_btn);

        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(getApplicationContext());
        String token = sharedPreferenceUtil.getData("token", null);

        RetrofitProvider retrofitProvider = new AuthRetrofitProvider(token);
        retrofitService = retrofitProvider.getService();
    }

    private void findAllAddress(){
        Intent intent = getIntent();
        long appointmentId = intent.getLongExtra("appointmentId", -1);
        Call<ResponseAPI<List<FindAllAddressResponse>>> findAddressCall = retrofitService.findAllAddress(appointmentId);
        findAddressCall.enqueue(new Callback<ResponseAPI<List<FindAllAddressResponse>>>() {
            @Override
            public void onResponse(Call<ResponseAPI<List<FindAllAddressResponse>>> call, Response<ResponseAPI<List<FindAllAddressResponse>>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(PlaceResultActivity.this, "주소 조회 실패했습니다.", Toast.LENGTH_SHORT).show();
                    try {
                        Log.e("주소 조회 실패", response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }

                for(FindAllAddressResponse findAddress : response.body().getData()){
                    userLocationList.add(new UserLocation(findAddress.getParticipantName(),
                            findAddress.getAddress(), findAddress.getLatitude(), findAddress.getLongitude()));
                }
                setParticipantList();
                setMarker();
            }

            @Override
            public void onFailure(Call<ResponseAPI<List<FindAllAddressResponse>>> call, Throwable t) {
                Toast.makeText(PlaceResultActivity.this, "서버 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                Log.e("서버 연결에 실패", t.getMessage());
            }
        });

    }

    private void setParticipantList(){
        listAdapter.setList(userLocationList);
        recyclerView.setAdapter(listAdapter);
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
        marker.setMap(naverMap);
        marker.setIconTintColor(Color.RED);
        setPlaceName(center);

        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(center);
        naverMap.moveCamera(cameraUpdate);
    }

    private void setPlaceName(LatLng latLng){
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.KOREA);
        try {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            placeNameText.setText(addressList.get(0).getAddressLine(0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // 권한 거부됨
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        // 권한 확인, 결과는 onRequestPermissionResult 콜백 메서드 호출
        ActivityCompat.requestPermissions(this, PERMISSION, LOCATION_PERMISSION_REQUEST_CODE);

        findAllAddress();
    }
}