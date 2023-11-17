package com.example.wwmeet_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.wwmeet_android.domain.Appointment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button enterBtn;
    Button createApmBtn;
    List<Appointment> appointmentList = new ArrayList<>();
    SharedPreferenceUtil sharedPreferenceUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getAppointmentList();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        AppointmentBeforeAdapter beforeAdapter = new AppointmentBeforeAdapter();
//        appointmentList.add(new Appointment("1234123", "hello", 2, "강남", LocalDateTime.now(), LocalDateTime.now()));
//        beforeAdapter.setList(appointmentList); // 실제 데이터로 바꿔야 함 Todo
        recyclerView.setAdapter(beforeAdapter);

        beforeAdapter.setItemClickListener(new AppointmentBeforeAdapter.OnItemClickEventListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Todo 약속 확인 하기
                
            }
        });
        createApmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 약속 만들기
                Intent intent = new Intent(getApplicationContext(), AppointmentCreateActivity.class);
                startActivity(intent);
                finish();
            }
        });

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VoteScheduleActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void init(){
        recyclerView = findViewById(R.id.home_recyclerview);
        enterBtn = findViewById(R.id.home_enter_appoint_btn);
        createApmBtn = findViewById(R.id.home_create_appoint_btn);

        sharedPreferenceUtil = new SharedPreferenceUtil(getApplicationContext());
    }

    private void getAppointmentList(){
        Set<String> codes = sharedPreferenceUtil.getData("codes", new HashSet<>());
        // Todo DB 에서 약속 코드로 호출

    }
}