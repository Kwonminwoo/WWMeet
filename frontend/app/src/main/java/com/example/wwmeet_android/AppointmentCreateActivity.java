package com.example.wwmeet_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AppointmentCreateActivity extends AppCompatActivity {

    EditText nameEdit,usernameEdit,placeEdit;
    Button createBtn;
    ImageButton addBtn, minusBtn;
    TextView numText;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_create);
        init();
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                numText.setText(count+"");
            }
        });
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                numText.setText(count+"");
            }
        });
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SetVoteDeadlineActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void init(){
        nameEdit = findViewById(R.id.appointment_create_name_edit);
        usernameEdit = findViewById(R.id.appointment_create_user_name_edit);
        placeEdit = findViewById(R.id.appointment_create_place_edit);
        createBtn = findViewById(R.id.appointment_create_btn);
        addBtn = findViewById(R.id.appointment_create_add_btn);
        minusBtn = findViewById(R.id.appointment_create_minus_btn);
        numText = findViewById(R.id.appointment_create_num_people_text);
        numText.setText(count+"");
    }
}

