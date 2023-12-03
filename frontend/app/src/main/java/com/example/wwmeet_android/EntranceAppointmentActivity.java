package com.example.wwmeet_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wwmeet_android.dto.AddParticipantRequest;
import com.example.wwmeet_android.network.RetrofitProvider;
import com.example.wwmeet_android.network.RetrofitService;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntranceAppointmentActivity extends AppCompatActivity {

    EditText nameEdit,codeEdit;
    Button confirmBtn;
    private RetrofitService retrofitService;
    private SharedPreferenceUtil sharedPreferenceUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);
        init();

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddParticipantRequest addParticipantRequest = new AddParticipantRequest(nameEdit.getText().toString(), codeEdit.getText().toString());

                Call<Long> entranceCall = retrofitService.addParticipantByCode(addParticipantRequest);
                entranceCall.enqueue(new Callback<Long>() {
                    @Override
                    public void onResponse(Call<Long> call, Response<Long> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(EntranceAppointmentActivity.this, "약속 입장에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            try {
                                Log.e("약속 입장 실패", response.errorBody().string());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            return;
                        }
                        Long appointmentId = response.body();
                        String appointmentAndParticipant = appointmentId + " " + nameEdit.getText().toString();

                        Set<String> appointmentSet = sharedPreferenceUtil.getData("appointment", new HashSet<>());
                        Log.e("set size", appointmentSet.size() + "");
                        appointmentSet.add(appointmentAndParticipant);
                        sharedPreferenceUtil.putData("appointment", appointmentSet);

                        finish();
                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {
                        Toast.makeText(EntranceAppointmentActivity.this, "서버와의 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        Log.e("약속 입장 실패", t.getMessage());
                    }
                });

            }
        });
    }
    private void init() {
        nameEdit = findViewById(R.id.entrance_name_edit);
        codeEdit = findViewById(R.id.entrance_code_edit);
        confirmBtn = findViewById(R.id.entrance_confirm_btn);

        sharedPreferenceUtil = new SharedPreferenceUtil(getApplicationContext());
        RetrofitProvider retrofitProvider = new RetrofitProvider();
        retrofitService = retrofitProvider.getService();
    }

}

