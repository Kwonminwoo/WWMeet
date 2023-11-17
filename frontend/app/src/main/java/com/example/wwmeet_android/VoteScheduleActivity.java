package com.example.wwmeet_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;

public class VoteScheduleActivity extends AppCompatActivity {
    DatePicker datePicker;
    TimePicker timePicker;
    TextView dateText;
    Button schedulecreateBtn;
    ImageButton scheduleaddBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_schedule);
        init();
        schedulecreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AppointmentInfoBeforeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private LocalDateTime setLimitTime(){
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        LocalDateTime limit = LocalDateTime.of(year, month, day, hour, minute);
        return limit;
    }
    private void init(){
        schedulecreateBtn = findViewById(R.id.vote_schedule_create_btn);
        dateText = findViewById(R.id.vote_schedule_date_text);
        scheduleaddBtn = findViewById(R.id.vote_schedule_add_btn);
    }
}

