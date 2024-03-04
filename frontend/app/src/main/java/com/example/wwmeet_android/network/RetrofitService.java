package com.example.wwmeet_android.network;

import com.example.wwmeet_android.dto.AddParticipantRequest;
import com.example.wwmeet_android.dto.AppointmentScheduleResponse;
import com.example.wwmeet_android.dto.FindAllAddressResponse;
import com.example.wwmeet_android.dto.FindAppointmentListResponse;
import com.example.wwmeet_android.dto.FindAppointmentResponse;
import com.example.wwmeet_android.dto.FindParticipantResponse;
import com.example.wwmeet_android.dto.SaveAddressRequest;
import com.example.wwmeet_android.dto.SaveAppointmentRequest;
import com.example.wwmeet_android.dto.VoteScheduleRequest;
import com.example.wwmeet_android.member.dto.SignInRequest;
import com.example.wwmeet_android.member.dto.SignUpRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("/api/appointments/{id}")
    Call<FindAppointmentResponse> findAppointmentById(@Path("id") Long id);

    @POST("/api/appointments/participant")
    Call<Long> addParticipantByCode(@Body AddParticipantRequest addParticipantRequest);

    @POST("/api/appointments/{id}/vote")
    Call<Long> voteSchedule(@Path("id") Long id, @Body VoteScheduleRequest voteScheduleRequest);

    @GET("/api/appointments")
    Call<List<FindAppointmentListResponse>> findAppointmentList(@Query("appointmentIdList") List<Long> appointmentIdList);

    @POST("/api/appointments")
    Call<Long> saveAppointment(@Body SaveAppointmentRequest saveAppointmentRequest);

    @GET("/api/appointments/{id}/{name}/vote-status")
    Call<Boolean> getVoteStatusOfParticipant(@Path("id") Long id, @Path("name") String name);

    @GET("/api/appointments/code")
    Call<Long> findAppointmentByCode(@Query("code") String code);

    @GET("/api/appointments/{id}/date")
    Call<AppointmentScheduleResponse> getAppointmentSchedule(@Path("id") Long id);

    @GET("/api/participants/{appointment_id}")
    Call<List<FindParticipantResponse>> getAllParticipantOfAppointment(@Path("appointment_id") Long id);

    @POST("/api/address")
    Call<Void> saveAddress(@Body SaveAddressRequest saveAddressRequest);

    @GET("/api/address/{appointment_id}")
    Call<List<FindAllAddressResponse>> findAllAddress (@Path("appointment_id") Long appointmentId);

    @GET("/api/restaurants/images")
    Call<List<String>> getRestaurantImageList(@Query("urlList") List<String> urlList);

    @POST("/api/members/signin")
    Call<Long> signIn(@Body SignInRequest signInRequest);

    @POST("/api/members/signup")
    Call<Void> signUp(@Body SignUpRequest signUpRequest);
}
