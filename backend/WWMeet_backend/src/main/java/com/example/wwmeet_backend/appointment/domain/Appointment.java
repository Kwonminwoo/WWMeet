package com.example.wwmeet_backend.appointment.domain;


import com.example.wwmeet_backend.member.domain.Member;
import com.example.wwmeet_backend.participant.domain.Participant;
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

@Entity
public class Appointment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private List<Participant> participantList = new ArrayList<>();

    protected Appointment() {
    }

    private Appointment(Long id, String name, String place,
        String identificationCode, int participantNum, LocalDateTime voteDeadline) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.identificationCode = identificationCode;
        this.participantNum = participantNum;
        this.voteDeadline = voteDeadline;
    }

    public static Appointment of(Long id, String appointmentName, String appointmentPlace,
        String identificationCode, int participantNum, LocalDateTime voteDeadline){
        return new Appointment(id, appointmentName, appointmentPlace, identificationCode, participantNum, voteDeadline);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public String getIdentificationCode() {
        return identificationCode;
    }

    public int getParticipantNum() {
        return participantNum;
    }

    public LocalDateTime getVoteDeadline() {
        return voteDeadline;
    }

    public List<Participant> getParticipantList() {
        return participantList;
    }
}