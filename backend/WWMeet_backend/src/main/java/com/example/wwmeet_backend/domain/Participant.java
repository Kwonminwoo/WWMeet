package com.example.wwmeet_backend.domain;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(indexes = {
        @Index(columnList = "id"),
        @Index(columnList = "appointment_id")
})
public class Participant {
    @Id @GeneratedValue
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    private String participantName;
}