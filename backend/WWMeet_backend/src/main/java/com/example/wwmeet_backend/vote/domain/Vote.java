package com.example.wwmeet_backend.vote.domain;

import com.example.wwmeet_backend.participant.domain.Participant;
import com.example.wwmeet_backend.possibleschedule.domain.PossibleSchedule;
import lombok.*;

import javax.persistence.*;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(
        indexes = {
                @Index(columnList = "id"),
                @Index(columnList = "participant_id"),
                @Index(columnList = "possible_schedule_id")
        }
)
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
}
