package com.example.wwmeet_backend.vote.domain;

import com.example.wwmeet_backend.participant.domain.Participant;
import com.example.wwmeet_backend.possibleschedule.domain.PossibleSchedule;
import lombok.*;

import jakarta.persistence.*;

@Entity
public class Vote {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "participant_id")
    private Participant participant;

    @Setter
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "possible_schedule_id")
    private PossibleSchedule possibleSchedule;

    protected Vote() {
    }

    private Vote(Long id, Participant participant, PossibleSchedule possibleSchedule) {
        this.id = id;
        this.participant = participant;
        this.possibleSchedule = possibleSchedule;
    }

    public static Vote of(Long id, Participant participant, PossibleSchedule possibleSchedule){
        return new Vote(id, participant, possibleSchedule);
    }

    public Long getId() {
        return id;
    }

    public Participant getParticipant() {
        return participant;
    }

    public PossibleSchedule getPossibleSchedule() {
        return possibleSchedule;
    }
}
