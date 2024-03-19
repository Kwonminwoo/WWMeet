package com.example.wwmeet_backend.domain.appointment.util;

import com.example.wwmeet_backend.domain.appointment.domain.Appointment;
import com.example.wwmeet_backend.domain.appointment.dto.request.SaveAppointmentRequest;
import com.example.wwmeet_backend.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AppointmentDtoMapper {
    public static Appointment toEntity(SaveAppointmentRequest request, Member member) {
        Appointment appointment = Appointment.builder()
            .name(request.getAppointmentName())
            .place(request.getAppointmentPlace())
            .participantNum(request.getParticipantNum())
            .voteDeadline(request.getVoteDeadline())
            .member(member)
            .build();

        appointment.createIdentificationCode();

        return appointment;
    }
}
