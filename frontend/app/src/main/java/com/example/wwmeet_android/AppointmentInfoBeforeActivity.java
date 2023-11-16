package com.example.wwmeet_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AppointmentInfoBeforeActivity extends AppCompatActivity {

    TextView nameText, placeText, numText, deadlineText, inviteText;
    Button createBtn;
    ImageButton copyBtn, numBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_info_before);
        init();
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
    }
}
