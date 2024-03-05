package com.example.wwmeet_backend.participant.domain;

import com.example.wwmeet_backend.appointment.domain.Appointment;
import com.example.wwmeet_backend.member.domain.Member;
import com.example.wwmeet_backend.vote.domain.Vote;
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
import lombok.Getter;

@Entity
@Getter
public class Participant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    private String participantName;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "participant", fetch = FetchType.LAZY)
    private List<Vote> voteList = new ArrayList<>();

    protected Participant() {
    }

    private Participant (Appointment appointment, String participantName){
        this.appointment = appointment;
        this.participantName = participantName;
    }

    public static Participant of(Appointment appointment, String participantName) {
        return new Participant(appointment, participantName);
    }

}