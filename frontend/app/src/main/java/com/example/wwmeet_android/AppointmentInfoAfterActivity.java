package com.example.wwmeet_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AppointmentInfoAfterActivity extends AppCompatActivity {

    TextView nameText, placeText, numText;
    TextView vote_result_Text1, vote_result_Text2, vote_result_Text3;
    Button createBtn, foodBtn;
    ImageButton arrowBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_info_after);
        init();
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
    }
}
