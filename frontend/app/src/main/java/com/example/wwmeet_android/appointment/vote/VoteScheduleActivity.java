package com.example.wwmeet_android.appointment.vote;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wwmeet_android.R;
import com.example.wwmeet_android.appointment.info.AppointmentInfoBeforeActivity;
import com.example.wwmeet_android.dto.PossibleScheduleRequest;
import com.example.wwmeet_android.dto.VoteScheduleRequest;
import com.example.wwmeet_android.network.RetrofitProvider;
import com.example.wwmeet_android.network.RetrofitService;

import java.io.IOException;
import java.time.LocalDateTime;
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

    private LinearLayout dateTimeBox;
    private LinearLayout dateTimeBox2;
    private TextView dateTimeText;
    private TextView dateTimeText2;
    private int dateTimeId = 1;
    private ImageView deleteBtn;
    private ImageView deleteBtn2;

    private List<PossibleScheduleRequest> voteDateTimeList = new ArrayList<>();

    private List<Integer> boxIdList = new ArrayList<>();
    private List<Integer> dateTimeIdList = new ArrayList<>();

    private long nowAppointmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_schedule);
        init();
        setNowDate();
        voteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVoteList();
                setVoteStatus();
                voteScheduleTime();
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
                addDateTime();
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
    private LocalDateTime voteScheduleTime(){
        Intent intent = getIntent();
        long appointmentId = intent.getLongExtra("appointmentId", 0);
        nowAppointmentId = appointmentId;
        String participantName = intent.getStringExtra("participantName");
        Log.e("participant", participantName);

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
        dateTimeText = findViewById(R.id.vote_schedule_dateTime);
        dateTimeBox = findViewById(R.id.vote_schedule_dateTime_box);
        deleteBtn = findViewById(R.id.vote_schedule_delete_btn);
        mainBox = findViewById(R.id.vote_schedule_main_box);

        dateTimeBox2 = findViewById(R.id.vote_schedule_dateTime_box2);
        dateTimeText2 = findViewById(R.id.vote_schedule_dateTime2);
        deleteBtn2 = findViewById(R.id.vote_schedule_delete_btn2);

        RetrofitProvider retrofitProvider = new RetrofitProvider();
        retrofitService = retrofitProvider.getService();

    }

    private void setNowDate(){
        String nowDate = datePicker.getYear() + " - " + (datePicker.getMonth() + 1) + " - " + datePicker.getDayOfMonth();
        String nowStartTime = startTimePicker.getHour() + " : " + startTimePicker.getMinute();
        String nowEndTime = endTimePicker.getHour() + " : " + endTimePicker.getMinute();
        if (dateTimeId == 1) {
            dateText.setText(nowDate);
            dateTimeText.setText(nowDate + "  /  " + nowStartTime + "  ~  " + nowEndTime);
        }else{
            dateTimeText2.setText(nowDate + "  /  " + nowStartTime + "  ~  " + nowEndTime);
        }
    }

    private void addDateTime(){
        dateTimeId++;
        dateTimeBox2.setVisibility(View.VISIBLE);
        setNowDate();
        setVoteList();

//        LinearLayout box = new LinearLayout(this);
//        box.setOrientation(LinearLayout.HORIZONTAL);
//        box.setBackground(ContextCompat.getDrawable(this, R.drawable.background_box2));
//        int boxId = View.generateViewId();
//        boxIdList.add(boxId);
//        box.setId(boxId);
//
//        TextView dateTime = new TextView(this);
//        dateTime.setId(R.id.vote_schedule_dateTime + dateTimeId);
//        int dateTimeId = View.generateViewId();
//        dateTime.setId(dateTimeId);
//
//        box.addView(dateTime);
//
//        View view = new View(this);
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
//        params.weight = 1;
//        params.width = 0;
//        params.height = 0;
//
//        box.addView(view);
//
//        ImageView imageView = new ImageView(this);
//        imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.delete_btn));
//        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        ViewGroup.LayoutParams imageParams = (ViewGroup.LayoutParams) imageView.getLayoutParams();
//        imageParams.width = 25;
//        imageParams.height = 25;
//
//        box.addView(imageView);
//
//
//
//        mainBox.addView(box);
    }

    private void setVoteList(){
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        int startHour = startTimePicker.getHour();
        int startMinute = startTimePicker.getMinute();
        int endHour = endTimePicker.getHour();
        int endMinute = endTimePicker.getMinute();


        voteDateTimeList.add(new PossibleScheduleRequest(LocalDateTime.of(year, month + 1, day, startHour, startMinute).toString()
                , LocalDateTime.of(year, month + 1, day, endHour, endMinute).toString()));
    }

    private void setVoteStatus(){
    }
}

