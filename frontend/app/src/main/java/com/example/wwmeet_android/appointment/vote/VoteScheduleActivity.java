package com.example.wwmeet_android.appointment.vote;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.wwmeet_android.R;
import com.example.wwmeet_android.appointment.info.AppointmentInfoBeforeActivity;
import com.example.wwmeet_android.database.SharedPreferenceUtil;
import com.example.wwmeet_android.dto.AddressRequest;
import com.example.wwmeet_android.dto.PossibleScheduleRequest;
import com.example.wwmeet_android.dto.SaveAddressRequest;
import com.example.wwmeet_android.dto.VoteScheduleRequest;
import com.example.wwmeet_android.network.AuthRetrofitProvider;
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
    TextView dateText;
    Button voteBtn;
    ImageButton scheduleAddBtn;
    private LinearLayout mainBox;
    private RetrofitService retrofitService;
    private List<PossibleScheduleRequest> voteDateTimeList = new ArrayList<>();
    private long nowAppointmentId;
    private List<TextView> dateTimeTextList = new ArrayList<>();
    private TextView nowDateTimeTextView;
    private FrameLayout startTimePickerBox;
    private FrameLayout endTimePickerBox;
    private TextView startTimeText, endTimeText;
    private ImageView startTimePlusBtn, startTimeMinusBtn, endTimePlusBtn, endTimeMinusBtn;
    private int startTime = 1;
    private int endTime = 1;
    private TextView startTimeAfternoonBtn, startTimeMorningBtn, endTimeAfternoonBtn, endTimeMorningBtn;
    boolean isStartAfternoon = true, isEndAfternoon = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_schedule);
        startTimePickerBox = findViewById(R.id.vote_schedule_start_time);
        endTimePickerBox = findViewById(R.id.vote_schedule_end_time);

        LayoutInflater startTimeLayoutInflater = getLayoutInflater();
        startTimeLayoutInflater.inflate(R.layout.custom_time_picker, startTimePickerBox, true);

        LayoutInflater endTimeLayoutInflater = getLayoutInflater();
        endTimeLayoutInflater.inflate(R.layout.custom_time_picker, endTimePickerBox, true);

        init();
        setNewDateBox();
        setNowDate();

        startTimePlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(startTime == 12){
                    startTime = 0;
                }
                startTimeText.setText(String.valueOf(++startTime));
                setNowDate();
            }
        });

        startTimeMinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(startTime == 1){
                    return;
                }
                startTimeText.setText(String.valueOf(--startTime));
                setNowDate();
            }
        });

        endTimePlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(endTime == 12){
                    endTime = 0;
                }
                endTimeText.setText(String.valueOf(++endTime));
                setNowDate();
            }
        });

        endTimeMinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(endTime == 1){
                    return;
                }
                endTimeText.setText(String.valueOf(--endTime));
                setNowDate();
            }
        });

        startTimeAfternoonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimeMorningBtn.setTextColor(Color.GRAY);
                startTimeAfternoonBtn.setTextColor(Color.BLACK);
                isStartAfternoon = true;
            }
        });

        startTimeMorningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimeMorningBtn.setTextColor(Color.BLACK);
                startTimeAfternoonBtn.setTextColor(Color.GRAY);
                isStartAfternoon = false;
            }
        });

        endTimeAfternoonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endTimeMorningBtn.setTextColor(Color.GRAY);
                endTimeAfternoonBtn.setTextColor(Color.BLACK);
                isEndAfternoon = true;
            }
        });

        endTimeMorningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endTimeMorningBtn.setTextColor(Color.BLACK);
                endTimeAfternoonBtn.setTextColor(Color.GRAY);
                isEndAfternoon = false;
            }
        });


        voteBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
            @Override
            public void onClick(View v) {
                if(checkTimeRange()){
                    setVoteList();
                    saveAddress();
                    voteScheduleTime();
                }
            }
        });

        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
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

    }
    private boolean checkTimeRange(){
        LocalTime nowStartTime = LocalTime.of(startTime, 0, 0);
        LocalTime nowEndTime = LocalTime.of(endTime, 0, 0);
        if(nowEndTime.isAfter(nowStartTime)){
            return true;
        }
        Toast.makeText(VoteScheduleActivity.this, "종료 시간과 시작 시간은 최소 1시간 이상 차이나야 합니다.", Toast.LENGTH_SHORT).show();
        return false;
    }
    private void voteScheduleTime(){
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
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void saveAddress() {
        Intent intent = getIntent();
        AddressRequest address = intent.getSerializableExtra("address", AddressRequest.class);
        long appointmentId = intent.getLongExtra("appointmentId", -1);
        String participantName = intent.getStringExtra("participantName");
        Call<Void> saveAddressCall = retrofitService.saveAddress(new SaveAddressRequest(appointmentId,
                participantName, address.getAddressName(), address.getLatitude(), address.getLongitude()));
        saveAddressCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(VoteScheduleActivity.this, "주소 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    try {
                        Log.e("주소 저장 실패", response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(VoteScheduleActivity.this, "서버 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                Log.e("서버 연결 실패", t.getMessage());
            }
        });

    }

    private void init(){
        voteBtn = findViewById(R.id.vote_schedule_create_btn);
        dateText = findViewById(R.id.vote_schedule_date_text);
        scheduleAddBtn = findViewById(R.id.vote_schedule_add_btn);
        datePicker = findViewById(R.id.vote_schedule_datePicker);
        mainBox = findViewById(R.id.vote_schedule_main_box);

        startTimeText = startTimePickerBox.findViewById(R.id.time_picker_time);
        endTimeText = endTimePickerBox.findViewById(R.id.time_picker_time);
        startTimePlusBtn = startTimePickerBox.findViewById(R.id.time_picker_plus);
        startTimeMinusBtn = startTimePickerBox.findViewById(R.id.time_picker_minus);
        endTimePlusBtn = endTimePickerBox.findViewById(R.id.time_picker_plus);
        endTimeMinusBtn = endTimePickerBox.findViewById(R.id.time_picker_minus);
        startTimeAfternoonBtn = startTimePickerBox.findViewById(R.id.time_picker_afternoon);
        startTimeMorningBtn = startTimePickerBox.findViewById(R.id.time_picker_morning);

        endTimeAfternoonBtn = endTimePickerBox.findViewById(R.id.time_picker_afternoon);
        endTimeMorningBtn = endTimePickerBox.findViewById(R.id.time_picker_morning);

        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(getApplicationContext());
        String token = sharedPreferenceUtil.getData("token", null);

        RetrofitProvider retrofitProvider = new AuthRetrofitProvider(token);
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

        int start = 0;
        if(isStartAfternoon){
            start += startTime + 12;
            if(start == 24){
                start = 0;
            }
        }

        String nowStartTime = "";
        if(start < 10){
            nowStartTime = "0" + start + "시";
        }else{
            nowStartTime = start + "시";
        }

        int end = 0;
        if(isEndAfternoon){
            end += endTime + 12;
            if(end == 24){
                end = 0;
            }
        }

        String nowEndTime = "";
        if (end < 10) {
            nowEndTime = "0" + end + "시";
        }else{
            nowEndTime = end + "시";
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

