package com.example.wwmeet_android;

import android.os.Bundle;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;

public class SetVoteDeadlineActivity extends AppCompatActivity {
    DatePicker datePicker;
    TimePicker timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_vote_deadline);
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
}
