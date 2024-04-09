package com.example.wwmeet_android.appointment.entrance;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wwmeet_android.database.SharedPreferenceUtil;
import com.example.wwmeet_android.network.AuthRetrofitProvider;
import com.example.wwmeet_android.network.ResponseAPI;
import com.example.wwmeet_android.util.LocalDatabaseUtil;
import com.example.wwmeet_android.R;
import com.example.wwmeet_android.domain.MyAppointment;
import com.example.wwmeet_android.dto.AddParticipantRequest;
import com.example.wwmeet_android.network.RetrofitProvider;
import com.example.wwmeet_android.network.RetrofitService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntranceAppointmentActivity extends AppCompatActivity {

    EditText nameEdit,codeEdit;
    Button confirmBtn;
    private RetrofitService retrofitService;
    private LocalDatabaseUtil database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);
        init();

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAppointment();
            }
        });
    }
    private void init() {
        nameEdit = findViewById(R.id.entrance_name_edit);
        codeEdit = findViewById(R.id.entrance_code_edit);
        confirmBtn = findViewById(R.id.entrance_confirm_btn);

        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(getApplicationContext());
        String token = sharedPreferenceUtil.getData("token", null);

        RetrofitProvider retrofitProvider = new AuthRetrofitProvider(token);
        retrofitService = retrofitProvider.getService();

        database = new LocalDatabaseUtil(getApplicationContext());
    }

    public void enterAppointment() {
        AddParticipantRequest addParticipantRequest = new AddParticipantRequest(
                nameEdit.getText().toString(), codeEdit.getText().toString());

        Call<ResponseAPI<Long>> entranceCall = retrofitService.addParticipantByCode(addParticipantRequest);
        entranceCall.enqueue(new Callback<ResponseAPI<Long>>() {
            @Override
            public void onResponse(Call<ResponseAPI<Long>> call, Response<ResponseAPI<Long>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(EntranceAppointmentActivity.this, "약속 입장에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    try {
                        Log.e("약속 입장 실패", response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }

                finish();
            }

            @Override
            public void onFailure(Call<ResponseAPI<Long>> call, Throwable t) {
                Toast.makeText(EntranceAppointmentActivity.this, "서버와의 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                Log.e("약속 입장 실패", t.getMessage());
            }
        });
    }

}

