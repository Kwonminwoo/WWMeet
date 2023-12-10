package com.example.wwmeet_android.appointment.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wwmeet_android.R;


public class PlaceResultActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayout userPlaceListBox;
    Button createBtn;

    UserLocationListAdapter listAdapter = new UserLocationListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_result);
        init();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Intent intent = new Intent(getApplicationContext(), .class);
                startActivity(intent); */
            }
        });
    }

    private void init(){
        recyclerView = findViewById(R.id.home_recyclerview);
        userPlaceListBox = findViewById(R.id.place_result_list_box);

        createBtn = findViewById(R.id.location_setting_create_btn);

    }

}