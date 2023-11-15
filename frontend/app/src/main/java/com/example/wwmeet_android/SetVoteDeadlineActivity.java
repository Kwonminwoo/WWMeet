package com.example.wwmeet_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;

public class SetVoteDeadlineActivity extends AppCompatActivity {
    DatePicker datePicker;
    TimePicker timePicker;
    Button deadlineBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_vote_deadline);
        init();
        deadlineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 약속 만들기
                Intent intent = new Intent(getApplicationContext(), EntranceActivity.class);
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
        deadlineBtn = findViewById(R.id.set_vote_deadline_btn);
    }
}

