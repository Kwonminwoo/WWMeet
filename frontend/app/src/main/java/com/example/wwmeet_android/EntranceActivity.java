package com.example.wwmeet_android;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EntranceActivity extends AppCompatActivity {

    EditText nameEdit,codeEdit;
    Button createBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);
        init();
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void init(){
        nameEdit = findViewById(R.id.entrance_name_edit);
        codeEdit = findViewById(R.id.entrance_code_edit);
        createBtn = findViewById(R.id.entrance_create_btn);
    }
}

