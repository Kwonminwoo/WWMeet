package com.example.wwmeet_backend.domain.appointment.domain;


import com.example.wwmeet_backend.domain.member.domain.Member;
import com.example.wwmeet_backend.domain.participant.domain.Participant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "place")
    private String place;

    @Column(name = "identification_code")
    private String identificationCode;

    @Column(name = "people_num")
    private int participantNum;

    @Column(name = "vote_deadline")
    private LocalDateTime voteDeadline;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "appointment")
    private final List<Participant> participantList = new ArrayList<>();

    @Builder
    private Appointment(Long id, String name, String place,
        String identificationCode, int participantNum, LocalDateTime voteDeadline, Member member) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.identificationCode = identificationCode;
        this.participantNum = participantNum;
        this.voteDeadline = voteDeadline;
        this.member = member;
    }

    public void createIdentificationCode() {
        UUID identificationCodeUUID = UUID.randomUUID();
        this.identificationCode = identificationCodeUUID.toString().substring(0, 15);
    }
}