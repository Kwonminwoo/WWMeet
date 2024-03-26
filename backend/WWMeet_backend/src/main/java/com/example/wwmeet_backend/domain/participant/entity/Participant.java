package com.example.wwmeet_backend.domain.participant.entity;

import com.example.wwmeet_backend.domain.appointment.entity.Appointment;
import com.example.wwmeet_backend.domain.member.entity.Member;
import com.example.wwmeet_backend.domain.vote.entity.Vote;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    private String participantName;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "participant", fetch = FetchType.LAZY)
    private final List<Vote> voteList = new ArrayList<>();

    @Builder
    private Participant(Long id, Appointment appointment, String participantName, Member member) {
        this.id = id;
        this.appointment = appointment;
        this.participantName = participantName;
        this.member = member;
    }
}