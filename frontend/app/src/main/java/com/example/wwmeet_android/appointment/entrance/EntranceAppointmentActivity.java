package com.example.wwmeet_android.appointment.entrance;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
                checkDuplicateAppointmentAndEnter(codeEdit.getText().toString());
            }
        });
    }
    private void init() {
        nameEdit = findViewById(R.id.entrance_name_edit);
        codeEdit = findViewById(R.id.entrance_code_edit);
        confirmBtn = findViewById(R.id.entrance_confirm_btn);

        RetrofitProvider retrofitProvider = new RetrofitProvider();
        retrofitService = retrofitProvider.getService();

        database = new LocalDatabaseUtil(getApplicationContext());
    }

    private void checkDuplicateAppointmentAndEnter(String targetCode){
        Call<Long> findAppointmentCall = retrofitService.findAppointmentByCode(targetCode);
        findAppointmentCall.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(EntranceAppointmentActivity.this, "중복 체크 조회 실패", Toast.LENGTH_SHORT).show();
                    try {
                        Log.e("중복 체크 조회 실패", response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }
                List<MyAppointment> allMyAppointment = database.findAllMyAppointment();
                for(MyAppointment myAppointment : allMyAppointment){
                    if(myAppointment.getAppointmentId() == response.body()) {
                        // 중복이 존재 한다면
                        Toast.makeText(EntranceAppointmentActivity.this, "이미 입장한 약속입니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                enterAppointment();
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Toast.makeText(EntranceAppointmentActivity.this, "서버 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                Log.e("서버 연결에 실패했습니다.", t.getMessage());
            }
        });

    }

    public void enterAppointment(){
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
                String participantName = nameEdit.getText().toString();

                MyAppointment myAppointment = new MyAppointment(appointmentId, participantName);
                LocalDatabaseUtil database = new LocalDatabaseUtil(getApplicationContext());
                database.saveMyAppointment(myAppointment);

                finish();
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Toast.makeText(EntranceAppointmentActivity.this, "서버와의 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                Log.e("약속 입장 실패", t.getMessage());
            }
        });
    }

}

