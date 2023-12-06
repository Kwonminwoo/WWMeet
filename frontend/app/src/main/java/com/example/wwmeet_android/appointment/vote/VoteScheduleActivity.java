package com.example.wwmeet_android.appointment.vote;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.wwmeet_android.R;
import com.example.wwmeet_android.appointment.info.AppointmentInfoBeforeActivity;
import com.example.wwmeet_android.dto.PossibleScheduleRequest;
import com.example.wwmeet_android.dto.VoteScheduleRequest;
import com.example.wwmeet_android.network.RetrofitProvider;
import com.example.wwmeet_android.network.RetrofitService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoteScheduleActivity extends AppCompatActivity {
    DatePicker datePicker;
    TimePicker startTimePicker;
    TimePicker endTimePicker;
    TextView dateText;
    Button voteBtn;
    ImageButton scheduleAddBtn;
    private LinearLayout mainBox;
    private RetrofitService retrofitService;
    private List<PossibleScheduleRequest> voteDateTimeList = new ArrayList<>();
    private long nowAppointmentId;

    private List<TextView> dateTimeTextList = new ArrayList<>();
    private TextView nowDateTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_schedule);
        init();
        setNewDateBox();
        setNowDate();

        voteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkTimeRange()){
                    setVoteList();
                    voteScheduleTime();
                }
            }
        });

        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                // 날짜 선택 마다 바뀜
                setNowDate();
            }
        });

        scheduleAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTimeRange()){
                    addDateTime();
                }
            }
        });

        startTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                setNowDate();
            }
        });

        endTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                setNowDate();
            }
        });


    }
    private boolean checkTimeRange(){
        LocalTime startTime = LocalTime.of(startTimePicker.getHour(), 0, 0);
        LocalTime endTime = LocalTime.of(endTimePicker.getHour(), 0, 0);
        if(endTime.isAfter(startTime)){
            return true;
        }
        Toast.makeText(VoteScheduleActivity.this, "종료 시간과 시작 시간은 최소 1시간 이상 차이나야 합니다.", Toast.LENGTH_SHORT).show();
        return false;
    }
    private LocalDateTime voteScheduleTime(){
        Intent intent = getIntent();
        long appointmentId = intent.getLongExtra("appointmentId", 0);
        nowAppointmentId = appointmentId;
        String participantName = intent.getStringExtra("participantName");

        VoteScheduleRequest voteScheduleRequest = new VoteScheduleRequest(participantName, voteDateTimeList);

        Call<Long> voteCall = retrofitService.voteSchedule(appointmentId, voteScheduleRequest);
        voteCall.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(VoteScheduleActivity.this, "일정 투표에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    try {
                        Log.e("일정 투표에 실패했습니다.", response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), AppointmentInfoBeforeActivity.class);
                intent.putExtra("appointmentId", response.body());
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Toast.makeText(VoteScheduleActivity.this, "서버 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                Log.e("서버 연결에 실패했습니다.", t.getMessage());
            }
        });
        return null;
    }

    private void init(){
        voteBtn = findViewById(R.id.vote_schedule_create_btn);
        dateText = findViewById(R.id.vote_schedule_date_text);
        scheduleAddBtn = findViewById(R.id.vote_schedule_add_btn);
        datePicker = findViewById(R.id.vote_schedule_datePicker);
        startTimePicker = findViewById(R.id.vote_schedule_start_timepicker);
        endTimePicker = findViewById(R.id.vote_schedule_end_timepicker);
        mainBox = findViewById(R.id.vote_schedule_main_box);

        RetrofitProvider retrofitProvider = new RetrofitProvider();
        retrofitService = retrofitProvider.getService();
    }

    private void setNowDate(){
        String nowDate = "";
        if(datePicker.getDayOfMonth() < 10){
            nowDate = datePicker.getYear() + "년 " + (datePicker.getMonth() + 1) + "월 0" + datePicker.getDayOfMonth() + "일";
            if(datePicker.getMonth() + 1 < 10){
                nowDate = datePicker.getYear() + "년 0" + (datePicker.getMonth() + 1) + "월 0" + datePicker.getDayOfMonth() + "일";
            }else{
                nowDate = datePicker.getYear() + "년 " + (datePicker.getMonth() + 1) + "월 0" + datePicker.getDayOfMonth() + "일";
            }
        }else{
            nowDate = datePicker.getYear() + "년 " + (datePicker.getMonth() + 1) + "월 " + datePicker.getDayOfMonth() + "일";
            if(datePicker.getMonth() + 1 < 10){
                nowDate = datePicker.getYear() + "년 0" + (datePicker.getMonth() + 1) + "월 " + datePicker.getDayOfMonth() + "일";
            }else{
                nowDate = datePicker.getYear() + "년 " + (datePicker.getMonth() + 1) + "월 " + datePicker.getDayOfMonth() + "일";
            }
        }

        dateText.setText(nowDate);

        String nowStartTime = startTimePicker.getHour() + "시";
        if(startTimePicker.getHour() < 10){
            nowStartTime = "0" + startTimePicker.getHour() + "시";
        }else{
            nowStartTime = startTimePicker.getHour() + "시";
        }

        String nowEndTime = endTimePicker.getHour() + "시";
        if (endTimePicker.getHour() < 10) {
            nowEndTime = "0" + endTimePicker.getHour() + "시";
        }else{
            nowEndTime = endTimePicker.getHour() + "시";
        }
        nowDateTimeTextView.setText(nowDate + "   " + nowStartTime + "  ~  " + nowEndTime);
    }

    private void addDateTime(){
        setNewDateBox();
        setNowDate();
    }

    private void setNewDateBox(){
        LinearLayout box = new LinearLayout(this);

        box.setOrientation(LinearLayout.HORIZONTAL);
        box.setBackground(ContextCompat.getDrawable(this, R.drawable.background_box1));
        LinearLayout.LayoutParams boxParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        boxParams.leftMargin = 13;
        boxParams.rightMargin = 13;
        boxParams.topMargin = 13;
        boxParams.bottomMargin = 13;
        box.setLayoutParams(boxParams);

        int boxId = View.generateViewId();
        box.setId(boxId);

        // 시간 담을 textView
        TextView dateTime = new TextView(this);
        nowDateTimeTextView = dateTime;

        int dateTimeId = View.generateViewId();
        dateTime.setId(dateTimeId);
        dateTime.setTextColor(Color.BLACK);
        dateTime.setTextSize(17);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.leftMargin = 200;
        dateTime.setLayoutParams(textParams);

        box.addView(dateTime);
        dateTimeTextList.add(dateTime);

        // view
        View view = new View(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 0, 1);
        view.setLayoutParams(params);


        box.addView(view);

        // 삭제 버튼
        ImageView imageView = new ImageView(this);

        imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.delete));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(55, 55);
        imageView.setLayoutParams(imageParams);

        int deleteBtnId = View.generateViewId();
        imageView.setId(deleteBtnId);

        box.addView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                box.setVisibility(View.GONE);
                dateTimeTextList.remove(dateTime);
            }
        });

        mainBox.addView(box);
    }

    private void setVoteList(){
//        int year = datePicker.getYear();
//        int month = datePicker.getMonth();
//        int day = datePicker.getDayOfMonth();
//        int startHour = startTimePicker.getHour();
//        int endHour = endTimePicker.getHour();

        for (TextView textView : dateTimeTextList) {
            String dateTimeText = textView.getText().toString();

            String yearString = dateTimeText.substring(0, 4);
            String monthString = dateTimeText.substring(6, 8);
            String dayString = dateTimeText.substring(10, 12);
            String startHourString = dateTimeText.substring(16, 18);
            String endHourString = dateTimeText.substring(24, 26);

            String startDateTime = yearString + "-" + monthString + "-" + dayString + "T" + startHourString + ":00";
            String endDateTime = yearString + "-" + monthString + "-" + dayString + "T" + endHourString + ":00";

            voteDateTimeList.add(new PossibleScheduleRequest(startDateTime, endDateTime));
        }
    }
}

