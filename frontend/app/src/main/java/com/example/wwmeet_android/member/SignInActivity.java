package com.example.wwmeet_android.member;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wwmeet_android.MainActivity;
import com.example.wwmeet_android.R;
import com.example.wwmeet_android.member.dto.SignInRequest;
import com.example.wwmeet_android.network.RetrofitProvider;
import com.example.wwmeet_android.network.RetrofitService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    private EditText emailInput;
    private EditText passwordInput;

    private RetrofitService retrofitService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        init();

        emailInput = findViewById(R.id.login_email);
        passwordInput = findViewById(R.id.login_password);
        findViewById(R.id.login_login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();

                if (verifyLoginInfo(email, password)) {
                    Toast.makeText(SignInActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                    signIn(email, password);
                }
            }
        });

        findViewById(R.id.login_signUp_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init(){
        RetrofitProvider retrofitProvider = new RetrofitProvider();
        retrofitService = retrofitProvider.getService();
    }

    private boolean verifyLoginInfo(String email, String password){
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "이메일, 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void signIn(String email, String password){
        Call<Long> signInCall = retrofitService.signIn(new SignInRequest(email, password));
        signInCall.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(SignInActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                    try {
                        Log.e("약속 리스트 조회 실패", response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Toast.makeText(SignInActivity.this, "서버 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                Log.e("서버 연결 실패", t.getMessage());
            }
        });
    }

}