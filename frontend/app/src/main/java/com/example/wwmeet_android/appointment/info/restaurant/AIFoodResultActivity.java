package com.example.wwmeet_android.appointment.info.restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.wwmeet_android.R;

public class AIFoodResultActivity extends AppCompatActivity {
    private Button findRestaurantBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_food_result);

        findRestaurantBtn = findViewById(R.id.ai_food_result_restaurant_btn);
        findRestaurantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FoodSearchActivity.class);
                startActivity(intent);
            }
        });
    }


}