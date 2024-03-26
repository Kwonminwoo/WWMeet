package com.example.wwmeet_backend.domain.participant.util;

import com.example.wwmeet_backend.domain.appointment.entity.Appointment;
import com.example.wwmeet_backend.domain.member.entity.Member;
import com.example.wwmeet_backend.domain.participant.entity.Participant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ParticipantDtoMapper {
    public static Participant toEntity(String name, Appointment appointment, Member member) {
        return Participant.builder()
            .appointment(appointment)
            .participantName(name)
            .member(member)
            .build();
    }
}
