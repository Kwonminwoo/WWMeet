package com.example.wwmeet_android.appointment.create;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wwmeet_android.R;

public class LocationSettingActivity extends AppCompatActivity {

    EditText placeEdit;
    ImageView searchBtn;

    FrameLayout mapFrame;
    Button createBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_setting);
        init();

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(getApplicationContext(), AppointmentCreateActivity.class);
                startActivity(intent); */
            }
        });
    }
    private void init(){
        placeEdit = findViewById(R.id.location_setting_search_edit);
        searchBtn = findViewById(R.id.location_setting_search_btn);
        mapFrame = findViewById(R.id.location_setting_kakao_map);
        createBtn = findViewById(R.id.location_setting_create_btn);
    }
}

