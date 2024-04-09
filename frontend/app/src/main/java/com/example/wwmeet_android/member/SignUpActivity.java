package com.example.wwmeet_android.member;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wwmeet_android.R;
import com.example.wwmeet_android.member.dto.SignUpRequest;
import com.example.wwmeet_android.network.ResponseAPI;
import com.example.wwmeet_android.network.RetrofitProvider;
import com.example.wwmeet_android.network.RetrofitService;

import java.io.IOException;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private EditText emailInput;
    private EditText emailVerifyInput;
    private EditText passwordInput;
    private EditText passwordCheckInput;
    private boolean isEmailVerified;
    private RetrofitService retrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        init();

        findViewById(R.id.signup_email_verify_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getText().toString();
                if (verifyEmail(email)) {
                    authorizeEmail(email);
                }
            }
        });
        findViewById(R.id.signup_signup_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                String passwordCheck = passwordCheckInput.getText().toString();
                if (verifySignUpInfo(email, password, passwordCheck)) {
                    signUp(email, password);
                }
            }
        });
    }

    private void init(){
        emailInput = findViewById(R.id.signup_email);
        emailVerifyInput = findViewById(R.id.signup_email_verify);
        passwordInput = findViewById(R.id.signup_password);
        passwordCheckInput = findViewById(R.id.signup_password_check);

        RetrofitProvider retrofitProvider = new RetrofitProvider();
        retrofitService = retrofitProvider.getService();
    }

    private void authorizeEmail(String email){
        isEmailVerified = true;
    }

    private boolean verifyEmail(String email){
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern pattern = Pattern.compile(regex);
        if(pattern.matcher(email).matches()){
            return true;
        }
        return false;
    }

    private boolean verifySignUpInfo(String email, String password, String passwordCheck){
        if (email.isEmpty() || password.isEmpty() || passwordCheck.isEmpty()) {
            Toast.makeText(this, "회원가입 정보를 입력해주세요", Toast.LENGTH_SHORT).show();
            return false;
        }
        return password.equals(passwordCheck);
    }

    private void signUp(String email, String password){
        Call<ResponseAPI<Void>> signUp = retrofitService.signUp(new SignUpRequest(email, password));
        signUp.enqueue(new Callback<ResponseAPI<Void>>() {
            @Override
            public void onResponse(Call<ResponseAPI<Void>> call, Response<ResponseAPI<Void>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    try {
                        Log.e("회원가입 실패", response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }
                finish();
            }

            @Override
            public void onFailure(Call<ResponseAPI<Void>> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "서버 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                Log.e("서버 연결 실패", t.getMessage());
            }
        });
    }

}