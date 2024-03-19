package com.example.wwmeet_android.appointment.create;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wwmeet_android.appointment.vote.VoteScheduleActivity;
import com.example.wwmeet_android.database.SharedPreferenceUtil;
import com.example.wwmeet_android.network.AuthRetrofitProvider;
import com.example.wwmeet_android.util.LocalDatabaseUtil;
import com.example.wwmeet_android.R;
import com.example.wwmeet_android.appointment.info.AppointmentInfoBeforeActivity;
import com.example.wwmeet_android.domain.MyAppointment;
import com.example.wwmeet_android.dto.SaveAppointmentRequest;
import com.example.wwmeet_android.network.RetrofitProvider;
import com.example.wwmeet_android.network.RetrofitService;

import java.io.IOException;
import java.time.LocalDateTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetVoteDeadlineActivity extends AppCompatActivity {
    DatePicker datePicker;
    Button confirmBtn;
    private RetrofitService retrofitService;
    private FrameLayout timePickerBox;
    private TextView timeText;
    private TextView afternoonText;
    private TextView morningText;
    private int nowTime = 1;
    private boolean isAfternoon = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_vote_deadline);

        timePickerBox = findViewById(R.id.vote_deadline_time_picker_box);
        LayoutInflater layoutInflater = getLayoutInflater();
        layoutInflater.inflate(R.layout.custom_time_picker, timePickerBox, true);

        init();
        timePickerBox.findViewById(R.id.time_picker_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nowTime == 12){
                    nowTime = 0;
                }
                timeText.setText(String.valueOf(++nowTime));
            }
        });


        timePickerBox.findViewById(R.id.time_picker_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nowTime == 1){
                    return;
                }
                timeText.setText(String.valueOf(--nowTime));
            }
        });

        afternoonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afternoonText.setTextColor(Color.BLACK);
                morningText.setTextColor(Color.GRAY);
                isAfternoon = !isAfternoon;
            }
        });

        morningText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afternoonText.setTextColor(Color.GRAY);
                morningText.setTextColor(Color.BLACK);
                isAfternoon = !isAfternoon;
            }
        });
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
                            return;
                        }
                        Long appointmentId = response.body();

                        Intent intent = new Intent(getApplicationContext(), AppointmentInfoBeforeActivity.class);
                        intent.putExtra("appointmentId", appointmentId);
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

        int hour = Integer.parseInt(timeText.getText().toString());
        if(isAfternoon){
            hour += 12;
            if(hour == 24){
                hour = 0;
            }
        }
        return LocalDateTime.of(year, month + 1, day, hour, 0).toString();
    }
    private void init(){
        confirmBtn = findViewById(R.id.set_vote_deadline_create_btn);
        datePicker = findViewById(R.id.set_vote_deadline_datePicker);

        timeText = timePickerBox.findViewById(R.id.time_picker_time);
        afternoonText = timePickerBox.findViewById(R.id.time_picker_afternoon);
        morningText = timePickerBox.findViewById(R.id.time_picker_morning);


        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(getApplicationContext());
        String token = sharedPreferenceUtil.getData("token", null);

        RetrofitProvider retrofitProvider = new AuthRetrofitProvider(token);
        retrofitService = retrofitProvider.getService();
    }
}

