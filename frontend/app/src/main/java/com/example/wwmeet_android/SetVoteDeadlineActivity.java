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
    Button deadlinecreateBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_vote_deadline);
        init();
        deadlinecreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AppointmentInfoBeforeActivity.class);
                startActivity(intent);
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
        deadlinecreateBtn = findViewById(R.id.set_vote_deadline_create_btn);
    }
}

