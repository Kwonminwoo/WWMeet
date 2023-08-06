package com.example.wwmeet_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.wwmeet_android.domain.Appointment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button enterBtn;
    Button createApmBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        AppointmentBeforeAdapter beforeAdapter = new AppointmentBeforeAdapter();
        beforeAdapter.setList(new ArrayList<Appointment>()); // 실제 데이터로 바꿔야 함 Todo
        recyclerView.setAdapter(beforeAdapter);


        createApmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 약속 만들기
            }
        });

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 약속 입장 하기
            }
        });

    }

    private void init(){
        recyclerView = findViewById(R.id.home_recyclerview);
        enterBtn = findViewById(R.id.home_enter_appoint_btn);
        createApmBtn = findViewById(R.id.home_create_appoint_btn);
    }

}