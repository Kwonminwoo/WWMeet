package com.example.wwmeet_android.appointment.create;

import android.Manifest;
import android.content.Intent;
import android.graphics.PointF;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import com.example.wwmeet_android.R;
import com.example.wwmeet_android.appointment.vote.VoteScheduleActivity;
import com.example.wwmeet_android.dto.AddressRequest;
import com.naver.maps.geometry.LatLng;
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

public class LocationSettingActivity extends AppCompatActivity implements OnMapReadyCallback {

    EditText placeEdit;
    ImageView searchBtn;

    MapView mapFrame;
    Button createBtn;
    private TextView addressNameText;

    private static final String[] PERMISSION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private Marker marker = new Marker();

    private NaverMap naverMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private LatLng markerLatLng;
    private String addressName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_setting);
        init();

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });


        mapFrame.onCreate(savedInstanceState);
        mapFrame.getMapAsync(this);
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

    }
    private void init(){
        //placeEdit = findViewById(R.id.location_setting_search_edit);
        //searchBtn = findViewById(R.id.location_setting_search_btn);
        mapFrame = findViewById(R.id.location_setting_map_view);
        createBtn = findViewById(R.id.location_setting_create_btn);
        addressNameText = findViewById(R.id.location_address_name);
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
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
        // 권한 확인, 결과는 onRequestPermissionResult 콜백 메서드 호출
        ActivityCompat.requestPermissions(this, PERMISSION, LOCATION_PERMISSION_REQUEST_CODE);

        naverMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
                marker.setPosition(latLng);
                marker.setMap(naverMap);
                markerLatLng = latLng;
                setAddressName(latLng);
            }
        });
    }

    private void setAddressName(LatLng latLng){
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.KOREA);
        List<Address> fromLocation = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            try {
                fromLocation = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            } catch (IOException e) {

            }
        }
        if(!fromLocation.isEmpty()){
            String[] addressArray = fromLocation.get(0).getAddressLine(0).split(" ");
            String specificAddressName = "";
            for (int i = 1;i < addressArray.length;i++) {
                specificAddressName += addressArray[i] + " ";
            }
            addressNameText.setText(specificAddressName);
            addressName = specificAddressName;
        }
    }

    private void confirm(){
        Intent getIntent = getIntent();
        long appointmentId = getIntent.getLongExtra("appointmentId", -1);
        String participantName = getIntent.getStringExtra("participantName");
        AddressRequest addressRequest = new AddressRequest(addressName, markerLatLng.latitude, markerLatLng.longitude);

        Intent intent = new Intent(getApplicationContext(), VoteScheduleActivity.class);
        intent.putExtra("appointmentId", appointmentId);
        intent.putExtra("participantName", participantName);
        intent.putExtra("address", addressRequest);
        startActivity(intent);
    }
}

