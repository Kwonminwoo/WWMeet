package com.example.wwmeet_android.network;

import com.example.wwmeet_android.dto.AddParticipantRequest;
import com.example.wwmeet_android.dto.FindAppointmentListResponse;
import com.example.wwmeet_android.dto.FindAppointmentResponse;
import com.example.wwmeet_android.dto.SaveAppointmentRequest;
import com.example.wwmeet_android.dto.VoteScheduleRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitService {
    @GET("/api/appointments")
    Call<List<FindAppointmentListResponse>> findAppointmentList();

    @POST("/api/appointments/participant")
    Call<Long> addParticipantByCode(@Body AddParticipantRequest addParticipantRequest);

    @POST("/api/appointments/{id}/vote")
    Call<Long> voteSchedule(@Path("id") Long id, @Body VoteScheduleRequest voteScheduleRequest);

    @GET("/api/appointments/{id}")
    Call<FindAppointmentResponse> findAppointment(@Path("id") Long id);

    @POST("/api/appointments")
    Call<Long> saveAppointment(@Body SaveAppointmentRequest saveAppointmentRequest);

}
