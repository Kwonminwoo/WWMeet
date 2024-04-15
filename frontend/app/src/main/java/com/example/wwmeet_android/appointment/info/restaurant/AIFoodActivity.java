package com.example.wwmeet_android.appointment.info.restaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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
    private Button allBtn, koreanBtn, chineseBtn, westernBtn, japaneseBtn;
    private Button confirmBtn;
    private EditText feelEdit;
    private String foodType;
    private RetrofitAIService retrofitAIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_food);

        init();

        allBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtnColor("전체");
            }
        });

        koreanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtnColor("한식");
            }
        });

        chineseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtnColor("중식");
            }
        });

        westernBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtnColor("양식");
            }
        });

        japaneseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtnColor("일식");
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getFindAnalysis();
                Intent intent = new Intent(getApplicationContext(), AIFoodResultActivity.class);
                intent.putExtra("appointmentId", getIntent().getLongExtra("appointmentId", -1));
                intent.putExtra("ai data", "슬픔 89 카레 초콜릿 양식");
                intent.putExtra("ai data1", "마음이 힘들 때 편안하게 휴식을 취하세요.");
                startActivity(intent);
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

        Call<FindAnalysisResponse> analysisCall = retrofitAIService.findAnalysisById(feelEdit.getText().toString(), foodType);
        analysisCall.enqueue(new Callback<FindAnalysisResponse>() {
            @Override
            public void onResponse(Call<FindAnalysisResponse> call, Response<FindAnalysisResponse> response) {
                if (!response.isSuccessful()) {
                    try {
                        Log.e("감정 분석 실패", response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
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
                Log.e("(감정 분석) 서버 연결에 실패했습니다.", t.getMessage());
            }
        });

    }

    private void setBtnColor(String selectedFoodType){
        switch (selectedFoodType) {
            case "전체":
                allBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_select));
                koreanBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.button));
                chineseBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.button));
                westernBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.button));
                japaneseBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.button));
                foodType = "전체";
                break;
            case "한식":
                allBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button));
                koreanBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_select));
                chineseBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button));
                westernBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button));
                japaneseBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button));
                foodType = "한식";
                break;
            case "중식":
                allBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button));
                koreanBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button));
                chineseBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_select));
                westernBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button));
                japaneseBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button));
                foodType = "중식";
                break;
            case "양식":
                allBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button));
                koreanBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button));
                chineseBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button));
                westernBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_select));
                japaneseBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button));
                foodType = "양식";
                break;
            case "일식":
                allBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button));
                koreanBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button));
                chineseBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button));
                westernBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button));
                japaneseBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_select));
                foodType = "일식";
                break;
            default:
        }
    }
}