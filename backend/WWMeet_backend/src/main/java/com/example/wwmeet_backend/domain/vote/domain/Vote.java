package com.example.wwmeet_backend.domain.vote.domain;

import com.example.wwmeet_backend.domain.participant.domain.Participant;
import com.example.wwmeet_backend.domain.possibleschedule.domain.PossibleSchedule;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Setter;

@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public static Vote of(Long id, Participant participant, PossibleSchedule possibleSchedule) {
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
