package com.example.wwmeet_android.appointment.info;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wwmeet_android.MainActivity;
import com.example.wwmeet_android.R;
import com.example.wwmeet_android.dto.FindAppointmentResponse;
import com.example.wwmeet_android.network.RetrofitProvider;
import com.example.wwmeet_android.network.RetrofitService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentInfoBeforeActivity extends AppCompatActivity {

    TextView nameText, placeText, numText, deadlineText, inviteText;
    Button createBtn;
    ImageButton copyBtn, numBtn;

    private RetrofitService retrofitService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_info_before);
        init();
        getAppointmentData();

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void init(){
        nameText = findViewById(R.id.appointment_info_before_name_text);
        placeText = findViewById(R.id.appointment_info_before_place_text);
        numText = findViewById(R.id.appointment_info_before_participation_num_text);
        deadlineText = findViewById(R.id.appointment_info_before_vote_deadline_text);
        inviteText = findViewById(R.id.appointment_info_before_invite_link_text);
        copyBtn = findViewById(R.id.appointment_info_before_invite_link_copy_btn);
        numBtn = findViewById(R.id.appointment_info_before_num_arrow);
        createBtn = findViewById(R.id.appointment_info_before_create_btn);

        RetrofitProvider retrofitProvider = new RetrofitProvider();
        retrofitService = retrofitProvider.getService();
    }

    private void getAppointmentData(){
        Intent intent = getIntent();
        long appointmentId = intent.getLongExtra("appointmentId", 0L);
        Call<FindAppointmentResponse> findAppointmentCall = retrofitService.findAppointmentById(appointmentId);
        findAppointmentCall.enqueue(new Callback<FindAppointmentResponse>() {
            @Override
            public void onResponse(Call<FindAppointmentResponse> call, Response<FindAppointmentResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AppointmentInfoBeforeActivity.this, "약속 정보 조회에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    try {
                        Log.e("약속 정보 조회에 실패했습니다.", response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                setAppointmentData(response.body());
            }

            @Override
            public void onFailure(Call<FindAppointmentResponse> call, Throwable t) {
                Toast.makeText(AppointmentInfoBeforeActivity.this, "서버 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                Log.e("서버 연결에 실패했습니다.", t.getMessage());
            }
        });
    }

    private void setAppointmentData(FindAppointmentResponse appointmentData){
        placeText.setText(appointmentData.getAppointmentPlace());
        numText.setText(String.valueOf(appointmentData.getParticipantNum()));
        nameText.setText(appointmentData.getAppointmentName());
        deadlineText.setText(appointmentData.getDeadline());
        inviteText.setText(appointmentData.getIdentificationCode());
    }
}