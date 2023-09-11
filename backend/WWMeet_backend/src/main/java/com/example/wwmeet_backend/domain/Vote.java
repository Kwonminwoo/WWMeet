package com.example.wwmeet_backend.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.servlet.http.Part;
import java.time.LocalDateTime;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @ManyToOne
    @JoinColumn(name = "possible_schedule_id")
    private PossibleSchedule possibleSchedule;
}