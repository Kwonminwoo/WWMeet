package com.example.wwmeet_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wwmeet_android.domain.Appointment;
import com.example.wwmeet_android.dto.FindAppointmentListResponse;
import com.example.wwmeet_android.dto.FindAppointmentResponse;
import com.example.wwmeet_android.network.RetrofitProvider;
import com.example.wwmeet_android.network.RetrofitService;
import com.example.wwmeet_android.network.SseEventService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.security.auth.login.LoginException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayout appointmentListBox;
    Button enterBtn;
    Button createApmBtn;
    List<FindAppointmentListResponse> appointmentList = new ArrayList<>();
    private RetrofitService retrofitService;
    SharedPreferenceUtil sharedPreferenceUtil;
    ImageView mainLogo;
    ImageView smallLogo;
    AppointmentListAdapter listAdapter = new AppointmentListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        listAdapter.setItemClickListener(new AppointmentListAdapter.OnItemClickEventListener() {
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
                Intent intent = new Intent(getApplicationContext(), VoteScheduleActivity.class);
                startActivity(intent);
            }
        });

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
        });

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 입장하기
                Intent intent = new Intent(getApplicationContext(), EntranceAppointmentActivity.class);
                startActivity(intent);
            }
        });

        setSSE("test");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("resume", "resume");
        appointmentList.clear();
        getAppointmentList();
    }

    private void init(){
        recyclerView = findViewById(R.id.home_recyclerview);
        appointmentListBox = findViewById(R.id.main_apm_list_box);
        enterBtn = findViewById(R.id.home_enter_appoint_btn);
        createApmBtn = findViewById(R.id.home_create_appoint_btn);
        sharedPreferenceUtil = new SharedPreferenceUtil(getApplicationContext());
        mainLogo = findViewById(R.id.main_logo_img);
        smallLogo = findViewById(R.id.main_logo_small_img);

        RetrofitProvider retrofitProvider = new RetrofitProvider();
        retrofitService = retrofitProvider.getService();

        sharedPreferenceUtil = new SharedPreferenceUtil(getApplicationContext());
    }

    private void getAppointmentList(){
        Set<String> appointmentSet = sharedPreferenceUtil.getData("appointment", new HashSet<>());
        Log.e("set size", appointmentSet.size() + "");
        List<Long> idList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        for (String appointment : appointmentSet) {
            Log.e("appointments", appointment);
            String[] idAndName = appointment.split(" ");
            String id = idAndName[0];
            String name = idAndName[1];

            idList.add(Long.valueOf(id));
            nameList.add(name);
        }

        Call<List<FindAppointmentListResponse>> findAppointmentCall = retrofitService.findAppointment(idList);
        findAppointmentCall.enqueue(new Callback<List<FindAppointmentListResponse>>() {
            @Override
            public void onResponse(Call<List<FindAppointmentListResponse>> call, Response<List<FindAppointmentListResponse>> response) {
                if(!response.isSuccessful()) {
                    try {
                        Log.e("약속 리스트 조회 실패", response.errorBody().string());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                List<FindAppointmentListResponse> responseList = response.body();
                if (responseList != null) {
                    appointmentList = responseList;
                }
                setAppointmentList();
            }

            @Override
            public void onFailure(Call<List<FindAppointmentListResponse>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "서버 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                Log.e("서버 연결에 실패했습니다.", t.getMessage());
            }
        });

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
            appointmentListBox.setVisibility(View.VISIBLE);
        }
    }

    private void setAppointmentList() {
        listAdapter.setList(appointmentList);
        recyclerView.setAdapter(listAdapter);

        setLogoOrList();
    }
}