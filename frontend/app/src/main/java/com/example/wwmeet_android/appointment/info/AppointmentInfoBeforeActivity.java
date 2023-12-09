package com.example.wwmeet_android.appointment.info;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wwmeet_android.MainActivity;
import com.example.wwmeet_android.R;
import com.example.wwmeet_android.domain.Appointment;
import com.example.wwmeet_android.domain.Participant;
import com.example.wwmeet_android.dto.FindAppointmentResponse;
import com.example.wwmeet_android.dto.FindParticipantResponse;
import com.example.wwmeet_android.network.RetrofitProvider;
import com.example.wwmeet_android.network.RetrofitService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentInfoBeforeActivity extends AppCompatActivity {

    TextView nameText, placeText, numText, deadlineText, inviteText;
    Button createBtn;
    ImageButton copyBtn;
    ImageView numBtn;

    private RetrofitService retrofitService;

    private LinearLayout participantBox;
    private RecyclerView participantRecyclerView;
    private boolean participantBtn = false;

    private List<Participant> participantList = new ArrayList<>();
    private int participantNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_info_before);
        init();
        getAppointmentData();
        setParticipantList();

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AppointmentInfoBeforeActivity.this, "초대 코드가 복사 되었습니다.", Toast.LENGTH_SHORT).show();
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData code = ClipData.newPlainText("code", inviteText.getText().toString());
                clipboardManager.setPrimaryClip(code);
            }
        });

        numBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(participantBtn){
                    participantBox.setVisibility(View.GONE);
                    participantBtn = false;
                    return;
                }
                participantBox.setVisibility(View.VISIBLE);
                participantBtn = true;
            }
        });
    }
    private void init(){
        nameText = findViewById(R.id.appointment_info_before_name_text);
        placeText = findViewById(R.id.appointment_info_before_place_text);
        numText = findViewById(R.id.appointment_info_before_participation_num_text);
        deadlineText = findViewById(R.id.appointment_info_before_vote_deadline_text);
        inviteText = findViewById(R.id.appointment_info_before_invite_link_text);
        copyBtn = findViewById(R.id.appointment_info_before_invite_link_copy_btn);
        numBtn = findViewById(R.id.appointment_info_before_num_arrow);
        createBtn = findViewById(R.id.appointment_info_before_create_btn);
        participantBox = findViewById(R.id.appointment_info_before_participant_box);
        participantRecyclerView = findViewById(R.id.appointment_info_before_participant_list);

        RetrofitProvider retrofitProvider = new RetrofitProvider();
        retrofitService = retrofitProvider.getService();
    }

    private void getAppointmentData(){
        Intent intent = getIntent();
        long appointmentId = intent.getLongExtra("appointmentId", 0L);
        Call<FindAppointmentResponse> findAppointmentCall = retrofitService.findAppointmentById(appointmentId);
        findAppointmentCall.enqueue(new Callback<FindAppointmentResponse>() {
            @Override
            public void onResponse(Call<FindAppointmentResponse> call, Response<FindAppointmentResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AppointmentInfoBeforeActivity.this, "약속 정보 조회에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    try {
                        Log.e("약속 정보 조회에 실패했습니다.", response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                setAppointmentData(response.body());
            }

            @Override
            public void onFailure(Call<FindAppointmentResponse> call, Throwable t) {
                Toast.makeText(AppointmentInfoBeforeActivity.this, "서버 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                Log.e("서버 연결에 실패했습니다.", t.getMessage());
            }
        });
    }

    private void setAppointmentData(FindAppointmentResponse appointmentData){
        placeText.setText(appointmentData.getAppointmentPlace());
        numText.setText(String.valueOf(appointmentData.getParticipantNum()));
        nameText.setText(appointmentData.getAppointmentName());
        String[] dateAndTime = appointmentData.getDeadline().split("T");
        String[] date = dateAndTime[0].split("-");
        String[] time = dateAndTime[1].split(":");

        String dateTime = date[0] + "년 " + date[1] + "월 " + date[2] + "일\n"
                + time[0] + "시 " + time[1] + "분";

        deadlineText.setText(dateTime);
        inviteText.setText(appointmentData.getIdentificationCode());
        participantNum = appointmentData.getParticipantNum();
    }

    private void setParticipantList(){
        long appointmentId = getIntent().getLongExtra("appointmentId", 0L);
        Call<List<FindParticipantResponse>> findParticipantListCall = retrofitService.getAllParticipantOfAppointment(appointmentId);
        findParticipantListCall.enqueue(new Callback<List<FindParticipantResponse>>() {
            @Override
            public void onResponse(Call<List<FindParticipantResponse>> call, Response<List<FindParticipantResponse>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AppointmentInfoBeforeActivity.this, "참가자 조회에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    try {
                        Log.e("참가자 조회 실패", response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }
                for (FindParticipantResponse participantResponse : response.body()) {
                    Participant participant = new Participant(participantResponse.getParticipantName(), participantResponse.getVoteState());
                    participantList.add(participant);
                }
                setParticipantRecyclerView();
            }

            @Override
            public void onFailure(Call<List<FindParticipantResponse>> call, Throwable t) {
                Toast.makeText(AppointmentInfoBeforeActivity.this, "서버 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                Log.e("서버 연결 실패", t.getMessage());
            }
        });

    }

    private void setParticipantRecyclerView(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        participantRecyclerView.setLayoutManager(linearLayoutManager);

        ParticipantListAdapter participantListAdapter = new ParticipantListAdapter();
        participantListAdapter.setList(participantList, participantNum);

        participantRecyclerView.setAdapter(participantListAdapter);
    }
}
