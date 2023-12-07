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
import com.example.wwmeet_android.dto.AppointmentScheduleResponse;
import com.example.wwmeet_android.dto.FindAppointmentResponse;
import com.example.wwmeet_android.dto.ScheduleResponse;
import com.example.wwmeet_android.network.RetrofitProvider;
import com.example.wwmeet_android.network.RetrofitService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentInfoAfterActivity extends AppCompatActivity {

    TextView nameText, placeText, numText;
    TextView vote_result_Text1, vote_result_Text2, vote_result_Text3;
    Button createBtn, foodBtn;
    ImageButton arrowBtn;

    private RetrofitService retrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_info_after);

        init();
        setAppointmentData();
        setAppointmentSchedule();
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void init(){
        nameText = findViewById(R.id.appointment_info_after_name_text);
        placeText = findViewById(R.id.appointment_info_after_place_text);
        numText = findViewById(R.id.appointment_info_after_participation_num_text);
        arrowBtn = findViewById(R.id.appointment_info_after_num_arrow);
        vote_result_Text1 = findViewById(R.id.appointment_info_after_vote_result_text1);
        vote_result_Text2 = findViewById(R.id.appointment_info_after_vote_result_text2);
        vote_result_Text3 = findViewById(R.id.appointment_info_after_vote_result_text3);
        createBtn = findViewById(R.id.appointment_info_after_create_btn);
        foodBtn = findViewById(R.id.appointment_info_after_food_btn);

        RetrofitProvider retrofitProvider = new RetrofitProvider();
        retrofitService = retrofitProvider.getService();
    }

    private void setAppointmentData(){
        long appointmentId = getIntent().getLongExtra("appointmentId", 0L);
        Call<FindAppointmentResponse> getAppointmentCall = retrofitService.findAppointmentById(appointmentId);
        getAppointmentCall.enqueue(new Callback<FindAppointmentResponse>() {
            @Override
            public void onResponse(Call<FindAppointmentResponse> call, Response<FindAppointmentResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AppointmentInfoAfterActivity.this, "약속 조회에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    try {
                        Log.e("약속 조회 실패", response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }
                FindAppointmentResponse appointment = response.body();

                nameText.setText(appointment.getAppointmentName());
                placeText.setText(appointment.getAppointmentPlace());
                numText.setText(String.valueOf(appointment.getParticipantNum()));
            }

            @Override
            public void onFailure(Call<FindAppointmentResponse> call, Throwable t) {
                Toast.makeText(AppointmentInfoAfterActivity.this, "서버 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                Log.e("서버 연결에 실패했습니다.", t.getMessage());
            }
        });


    }

    private void setAppointmentSchedule(){
        long appointmentId = getIntent().getLongExtra("appointmentId", 0L);
        Call<AppointmentScheduleResponse> appointmentSchedule = retrofitService.getAppointmentSchedule(appointmentId);
        appointmentSchedule.enqueue(new Callback<AppointmentScheduleResponse>() {
            @Override
            public void onResponse(Call<AppointmentScheduleResponse> call, Response<AppointmentScheduleResponse> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(AppointmentInfoAfterActivity.this, "약속 날짜 조회를 실패했습니다.", Toast.LENGTH_SHORT).show();
                    try {
                        Log.e("약속 날짜 조회 실패", response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }
                AppointmentScheduleResponse appointmentSchedule = response.body();
                ScheduleResponse firstSchedule = appointmentSchedule.getFirstSchedule();
                ScheduleResponse secondSchedule = appointmentSchedule.getSecondSchedule();
                ScheduleResponse thirdSchedule = appointmentSchedule.getThirdSchedule();

                String rank1 = firstSchedule.getStartDateTime() + " ~ " + firstSchedule.getEndTime();
                String rank2;
                String rank3;
                if(secondSchedule != null){
                    rank2 = secondSchedule.getStartDateTime() + " ~ " + secondSchedule.getEndTime();
                    vote_result_Text2.setText(rank2);
                }
                if(secondSchedule != null){
                    rank3 = thirdSchedule.getStartDateTime() + " ~ " + thirdSchedule.getEndTime();
                    vote_result_Text3.setText(rank3);
                }
                vote_result_Text1.setText(rank1);
            }

            @Override
            public void onFailure(Call<AppointmentScheduleResponse> call, Throwable t) {
                Toast.makeText(AppointmentInfoAfterActivity.this, "서버 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                Log.e("서버 연결 실패", t.getMessage());
            }
        });
    }
}
