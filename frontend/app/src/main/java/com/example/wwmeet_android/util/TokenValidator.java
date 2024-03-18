package com.example.wwmeet_android.util;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.wwmeet_android.member.SignInActivity;

public class TokenValidator {
    private static final int EXPIRED = 302;
    public static void validateToken(int code, Context context) {
        if(code == EXPIRED){
            Toast.makeText(context, "로그인 정보가 만료되었습니다.", Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, SignInActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
}
