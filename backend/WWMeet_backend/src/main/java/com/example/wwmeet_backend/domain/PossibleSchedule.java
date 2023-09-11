package com.example.wwmeet_backend.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(indexes = {
        @Index(columnList = "id")
})
public class PossibleSchedule {
    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}