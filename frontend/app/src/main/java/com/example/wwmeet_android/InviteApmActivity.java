package com.example.wwmeet_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOError;
import java.util.HashSet;
import java.util.Set;

public class InviteApmActivity extends AppCompatActivity {
    TextView codeTextView;
    ImageView clipboardImageView;

    Button toHomeBtn;

    SharedPreferenceUtil sharedPreferenceUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_apm);
        init();

        Intent intent = getIntent();
        String appointmentCode = intent.getStringExtra("code"); // 방 코드 가져오기
        codeTextView.setText(appointmentCode);

        saveAppointment(appointmentCode); // 약속 저장

        clipboardImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클립 보드에 복사
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("code", appointmentCode);
                clipboardManager.setPrimaryClip(clip);
                Toast.makeText(InviteApmActivity.this, "코드가 복사되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        toHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHome = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentHome);

                finish();
            }
        });

    }

    private void init(){
        codeTextView = findViewById(R.id.invite_code);
        clipboardImageView = findViewById(R.id.invite_clipboard_btn);
        toHomeBtn = findViewById(R.id.invite_toHome_btn);

        sharedPreferenceUtil = new SharedPreferenceUtil(getApplicationContext());
    }

    private void saveAppointment(String appointmentCode){
        Set<String> appointmentCodes = sharedPreferenceUtil.getData("codes", new HashSet<>());
        // 저장된 약속들 가져옴 없다면 새로 만듬
        appointmentCodes.add(appointmentCode); // 약속 추가
        sharedPreferenceUtil.putData("codes", appointmentCodes);
    }
}