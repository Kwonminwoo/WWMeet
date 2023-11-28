package com.example.wwmeet_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wwmeet_android.dto.SaveAppointmentRequest;
import com.example.wwmeet_android.network.RetrofitProvider;
import com.example.wwmeet_android.network.RetrofitService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetVoteDeadlineActivity extends AppCompatActivity {
    DatePicker datePicker;
    TimePicker timePicker;
    Button confirmBtn;
    private RetrofitService retrofitService;
    private SharedPreferenceUtil sharedPreferenceUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_vote_deadline);
        init();
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deadline = setLimitTime();
                Intent getIntent = getIntent();
                SaveAppointmentRequest appointment = (SaveAppointmentRequest) getIntent.getSerializableExtra("appointment");
                appointment.setVoteDeadline(deadline);

                Call<Long> saveAppointmentCall = retrofitService.saveAppointment(appointment);
                saveAppointmentCall.enqueue(new Callback<Long>() {
                    @Override
                    public void onResponse(Call<Long> call, Response<Long> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(SetVoteDeadlineActivity.this, "약속 저장 실패", Toast.LENGTH_SHORT).show();
                            try {
                                Log.e("약속 저장 실패", response.errorBody().string());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        String appointmentAndParticipant = response.body() + " " + appointment.getParticipantName();
                        Set<String> appointmentSet = sharedPreferenceUtil.getData("appointment", new HashSet<>());
                        appointmentSet.add(appointmentAndParticipant);

                        Intent intent = new Intent(getApplicationContext(), AppointmentInfoBeforeActivity.class);
                        intent.putExtra("appointmentId", response.body());
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {
                        Toast.makeText(SetVoteDeadlineActivity.this, "약속 저장 실패", Toast.LENGTH_SHORT).show();
                        Log.e("약속 저장 실패", t.getMessage());
                    }
                });
            }
        });
    }
    private String setLimitTime(){
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();


        return LocalDateTime.of(year, month + 1, day, hour, minute).toString();
    }
    private void init(){
        confirmBtn = findViewById(R.id.set_vote_deadline_create_btn);
        datePicker = findViewById(R.id.set_vote_deadline_datePicker);
        timePicker = findViewById(R.id.set_vote_deadline_timepicker);

        RetrofitProvider retrofitProvider = new RetrofitProvider();
        retrofitService = retrofitProvider.getService();
        sharedPreferenceUtil = new SharedPreferenceUtil(getApplicationContext());
    }
}

