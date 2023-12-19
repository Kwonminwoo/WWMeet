package com.example.wwmeet_android.appointment.info.restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.wwmeet_android.R;

public class AIFoodActivity extends AppCompatActivity {
    private Button allBtn, anotherBtn, koreanBtn,
            chineseBtn, westernBtn, japaneseBtn;
    private Button confirmBtn;
    private EditText feelEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_food);

        init();

        allBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOption();
            }
        });

        anotherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOption();
            }
        });

        koreanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOption();
            }
        });

        chineseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOption();
            }
        });

        westernBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOption();
            }
        });

        japaneseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOption();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AIFoodResultActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init(){
        allBtn = findViewById(R.id.ai_food_option_all);
        anotherBtn = findViewById(R.id.ai_food_option_another);
        koreanBtn = findViewById(R.id.ai_food_option_korean);
        chineseBtn = findViewById(R.id.ai_food_option_chinese);
        westernBtn = findViewById(R.id.ai_food_option_western);
        japaneseBtn = findViewById(R.id.ai_food_option_japanese);
        confirmBtn = findViewById(R.id.ai_food_confirm_btn);

        feelEdit = findViewById(R.id.ai_food_feel_edit);
    }

    private void clickOption(){

    }
}