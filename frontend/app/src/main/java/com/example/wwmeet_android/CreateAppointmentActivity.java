package com.example.wwmeet_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.wwmeet_android.domain.Appointment;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateAppointmentActivity extends AppCompatActivity {
    EditText nameEdit;
    EditText placeEdit;
    EditText numPeopleEdit;
    Button createBtn;
    DatePicker datePicker;
    TimePicker timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);
        init();

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEdit.getText().toString();
                String place = placeEdit.getText().toString();
                String numPeople = numPeopleEdit.getText().toString();

                if(name.equals("") || place.equals("") || numPeople.equals("")){
                    Toast.makeText(CreateAppointmentActivity.this, "약속 정보를 올바르게 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }

//                String appointmentCode = UUID.randomUUID().toString(); // 랜덤으로 생성된 유니크 코드
                String appointmentCode = Long.toString(UUID.randomUUID().getMostSignificantBits()); // 생성된 방 코드
                // Todo 길이가 너무 길어서 줄일 수 있는지 찾기

                LocalDateTime limitTime = setLimitTime();
                Appointment newAppointment = new Appointment(appointmentCode, name, Integer.parseInt(numPeople), place, null, limitTime);

                // Todo newAppointment 데이터베이스에 저장

                Intent intent = new Intent(getApplicationContext(), InviteApmActivity.class);
                intent.putExtra("code", appointmentCode);
                startActivity(intent);
                finish();
            }
        });
    }

    private void init(){
        nameEdit = findViewById(R.id.create_appointment_name_edit);
        placeEdit = findViewById(R.id.create_appointment_place_edit);
        numPeopleEdit = findViewById(R.id.create_num_people_edit);
        createBtn = findViewById(R.id.create_create_btn);
        datePicker = findViewById(R.id.create_limit_date);
        timePicker = findViewById(R.id.create_limit_time);
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