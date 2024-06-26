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

import com.example.wwmeet_android.appointment.create.AppointmentCreateActivity;
import com.example.wwmeet_android.appointment.create.LocationSettingActivity;
import com.example.wwmeet_android.appointment.entrance.EntranceAppointmentActivity;
import com.example.wwmeet_android.appointment.info.AppointmentInfoAfterActivity;
import com.example.wwmeet_android.appointment.info.AppointmentInfoBeforeActivity;
import com.example.wwmeet_android.appointment.info.AppointmentListAdapter;
import com.example.wwmeet_android.appointment.info.restaurant.AIFoodActivity;
import com.example.wwmeet_android.appointment.vote.VoteScheduleActivity;
import com.example.wwmeet_android.database.SharedPreferenceUtil;
import com.example.wwmeet_android.domain.MyAppointment;
import com.example.wwmeet_android.dto.AppointmentScheduleResponse;
import com.example.wwmeet_android.dto.FindAppointmentListResponse;
import com.example.wwmeet_android.dto.ScheduleResponse;
import com.example.wwmeet_android.member.SignInActivity;
import com.example.wwmeet_android.network.AuthRetrofitProvider;
import com.example.wwmeet_android.network.ResponseAPI;
import com.example.wwmeet_android.network.RetrofitProvider;
import com.example.wwmeet_android.network.RetrofitService;
import com.example.wwmeet_android.network.SseEventService;
import com.example.wwmeet_android.util.LocalDatabaseUtil;
import com.example.wwmeet_android.util.TokenValidator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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
    ImageView mainLogo;
    ImageView smallLogo;
    AppointmentListAdapter listAdapter = new AppointmentListAdapter();
    private SharedPreferenceUtil sharedPreferenceUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        // 리스트 클릭하면 -> 투표 했는지 체크 후 정보 화면으로 넘어가야 함
        listAdapter.setItemClickListener(new AppointmentListAdapter.OnItemClickEventListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(appointmentList.get(position).isVoteFinish()){
                    // 전체 투표 끝난 후
                    Intent intent = new Intent(getApplicationContext(), AppointmentInfoAfterActivity.class);
                    intent.putExtra("appointmentId", appointmentList.get(position).getId());
                    startActivity(intent);
                }else{
                    // 전체 투표 끝나기 전
                    Call<ResponseAPI<Boolean>> voteStatusCall = retrofitService.getVoteStatusOfParticipant(
                            appointmentList.get(position).getId(), appointmentList.get(position).getParticipantName());

                    voteStatusCall.enqueue(new Callback<ResponseAPI<Boolean>>() {
                        @Override
                        public void onResponse(Call<ResponseAPI<Boolean>> call, Response<ResponseAPI<Boolean>> response) {
                            if(!response.isSuccessful()){
                                Toast.makeText(MainActivity.this, "투표 상태 조회에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                try {
                                    Log.e("투표 상태 조회에 실패했습니다.", response.errorBody().string());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                TokenValidator.validateToken(response.code(), getApplicationContext());
                                return;
                            }
                            Intent intent = null;
                            // 내가 투표를 했는지 / 안했는지
                            if (response.body().getData()) {
                                intent = new Intent(getApplicationContext(), AppointmentInfoBeforeActivity.class);
                                intent.putExtra("appointmentId", appointmentList.get(position).getId());
                            }else{
                                intent = new Intent(getApplicationContext(), LocationSettingActivity.class);
                                intent.putExtra("appointmentId", appointmentList.get(position).getId());
                                intent.putExtra("participantName", appointmentList.get(position).getParticipantName());
                            }
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<ResponseAPI<Boolean>> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "서버 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            Log.e("서버 연결에 실패했습니다.", t.getMessage());
                        }
                    });
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
                // 입장하기
                Intent intent = new Intent(getApplicationContext(), EntranceAppointmentActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.home_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "로그아웃하였습니다.", Toast.LENGTH_SHORT).show();
                sharedPreferenceUtil.remove("token");
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        appointmentList.clear();
        getAppointmentList();
    }

    private void init(){
        recyclerView = findViewById(R.id.home_recyclerview);
        appointmentListBox = findViewById(R.id.main_apm_list_box);
        enterBtn = findViewById(R.id.home_enter_appoint_btn);
        createApmBtn = findViewById(R.id.home_create_appoint_btn);
        mainLogo = findViewById(R.id.main_logo_img);
        smallLogo = findViewById(R.id.main_logo_small_img);

        sharedPreferenceUtil = new SharedPreferenceUtil(getApplicationContext());
        String token = sharedPreferenceUtil.getData("token", null);

        AuthRetrofitProvider retrofitProvider = new AuthRetrofitProvider(token);
        retrofitService = retrofitProvider.getService();
    }

    private void getAppointmentList(){
        Call<ResponseAPI<List<FindAppointmentListResponse>>> findListCall = retrofitService.findAppointmentList();
        findListCall.enqueue(new Callback<ResponseAPI<List<FindAppointmentListResponse>>>() {
            @Override
            public void onResponse(Call<ResponseAPI<List<FindAppointmentListResponse>>> call,
                                   Response<ResponseAPI<List<FindAppointmentListResponse>>> response) {
                if(!response.isSuccessful()) {
                    try {
                        Log.e("약속 리스트 조회 실패", response.errorBody().string());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    TokenValidator.validateToken(response.code(), getApplicationContext());
                }

                List<FindAppointmentListResponse> responseList = response.body().getData();
                if (responseList != null) {
                    appointmentList = responseList;
                    setAppointmentList();
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI<List<FindAppointmentListResponse>>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "서버 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                Log.e("(약속 리스트 조회) 서버 연결에 실패했습니다.", t.getMessage());
            }
        });
    }

    private void setLogoOrList(){
        if (!appointmentList.isEmpty()){
            mainLogo.setVisibility(View.GONE);
            smallLogo.setVisibility(View.VISIBLE);
            appointmentListBox.setVisibility(View.VISIBLE);
            findViewById(R.id.main_view_1).setVisibility(View.GONE);
            findViewById(R.id.main_view_2).setVisibility(View.VISIBLE);
        }
    }

    private void setAppointmentList() {
        listAdapter.setList(appointmentList);
        recyclerView.setAdapter(listAdapter);

        setLogoOrList();
    }

}