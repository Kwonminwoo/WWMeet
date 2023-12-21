package com.example.wwmeet_android.appointment.info.restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wwmeet_android.R;
import com.example.wwmeet_android.dto.FindAnalysisResponse;
import com.example.wwmeet_android.network.AIRetrofitProvider;
import com.example.wwmeet_android.network.RetrofitAIService;
import com.example.wwmeet_android.network.RetrofitProvider;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;


import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;


public class AIFoodActivity extends AppCompatActivity {
    private Button allBtn, koreanBtn,
            chineseBtn, westernBtn, japaneseBtn;
    private Button confirmBtn;
    private EditText feelEdit;

    private String foodtype;

    private RetrofitAIService retrofitAIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_food);

        init();

        allBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodtype = "전체";

                allBtn.setBackgroundColor(Color.GRAY);
            }
        });

        koreanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodtype = "한식";
                koreanBtn.setBackgroundColor(Color.GRAY);
            }
        });

        chineseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                foodtype = "중식";
                chineseBtn.setBackgroundColor(Color.GRAY);
            }
        });

        westernBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                foodtype = "양식";
                westernBtn.setBackgroundColor(Color.GRAY);
            }
        });

        japaneseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                foodtype = "일식";
                japaneseBtn.setBackgroundColor(Color.GRAY);
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFindAnalysis();

            }
        });

    }


    private void init(){
        allBtn = findViewById(R.id.ai_food_option_all);
        koreanBtn = findViewById(R.id.ai_food_option_korean);
        chineseBtn = findViewById(R.id.ai_food_option_chinese);
        westernBtn = findViewById(R.id.ai_food_option_western);
        japaneseBtn = findViewById(R.id.ai_food_option_japanese);
        confirmBtn = findViewById(R.id.ai_food_confirm_btn);

        feelEdit = findViewById(R.id.ai_food_feel_edit);

        AIRetrofitProvider aiRetrofitProvider = new AIRetrofitProvider();
        retrofitAIService = aiRetrofitProvider.getService();
    }

    private void getFindAnalysis() {

        Call<FindAnalysisResponse> analysisCall = retrofitAIService.findAnalysisById(feelEdit.getText().toString(),foodtype.toString());
        analysisCall.enqueue(new Callback<FindAnalysisResponse>() {
            @Override
            public void onResponse(Call<FindAnalysisResponse> call, Response<FindAnalysisResponse> response) {
                if (!response.isSuccessful()) {
//                    try {
                        Log.e("감정 분석 실패", response.message());
//                    } catch (IOException ex) {
//                        throw new RuntimeException(ex);
//                    }
                    return;
                }

                FindAnalysisResponse analysisResponse = response.body();
                if (analysisResponse != null) {
                    Intent intent = new Intent(getApplicationContext(), AIFoodResultActivity.class);
                    intent.putExtra("appointmentId", getIntent().getLongExtra("appointmentId", -1));
                    intent.putExtra("ai data", analysisResponse.getData());
                    intent.putExtra("ai data1", analysisResponse.getData1());
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<FindAnalysisResponse> call, Throwable t) {
                Toast.makeText(AIFoodActivity.this, "서버 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                Log.e("서버 연결에 실패했습니다.", t.getMessage());
            }
        });

    }
}