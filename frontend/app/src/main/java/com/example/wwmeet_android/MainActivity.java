package com.example.wwmeet_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wwmeet_android.domain.Appointment;
import com.example.wwmeet_android.dto.FindAppointmentListResponse;
import com.example.wwmeet_android.network.SseEventService;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button enterBtn;
    Button createApmBtn;
    List<FindAppointmentListResponse> appointmentList = new ArrayList<>();
    SharedPreferenceUtil sharedPreferenceUtil;
    ImageView mainLogo;
    ImageView smallLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getAppointmentList();
        setLogoOrList();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        AppointmentListAdapter beforeAdapter = new AppointmentListAdapter();
//        appointmentList.add(new Appointment("1234123", "hello", 2, "강남", LocalDateTime.now(), LocalDateTime.now()));
//        beforeAdapter.setList(appointmentList); // 실제 데이터로 바꿔야 함 Todo
        recyclerView.setAdapter(beforeAdapter);

        beforeAdapter.setItemClickListener(new AppointmentListAdapter.OnItemClickEventListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Todo 약속 확인 하기
                if(appointmentList.get(position).isFinishVote()){
                    // 끝난 후 약속 요청
                }else{
                    // 끝나기 전 약속 요청
                }
            }
        });
        createApmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 약속 만들기
                Intent intent = new Intent(getApplicationContext(), AppointmentCreateActivity.class);
                startActivity(intent);
            }
        });

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EntranceAppointmentActivity.class);
                startActivity(intent);
            }
        });

        setSSE("test");

    }
    private void init(){
        recyclerView = findViewById(R.id.home_recyclerview);
        enterBtn = findViewById(R.id.home_enter_appoint_btn);
        createApmBtn = findViewById(R.id.home_create_appoint_btn);
        sharedPreferenceUtil = new SharedPreferenceUtil(getApplicationContext());
        mainLogo = findViewById(R.id.main_logo_img);
        smallLogo = findViewById(R.id.main_logo_small_img);
    }

    private void getAppointmentList(){
        Set<String> codes = sharedPreferenceUtil.getData("codes", new HashSet<>());
        // Todo DB 에서 약속 코드로 호출

    }

    private void setSSE(String key){
        SseEventService sseEventService = new SseEventService();
        try {
            sseEventService.startSse(key);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void setLogoOrList(){
        if (!appointmentList.isEmpty()){
            mainLogo.setVisibility(View.GONE);
            smallLogo.setVisibility(View.VISIBLE);
        }
    }
}