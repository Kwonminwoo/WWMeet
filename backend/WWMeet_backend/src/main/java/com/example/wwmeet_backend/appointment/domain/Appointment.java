package com.example.wwmeet_backend.appointment.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(
        indexes = {
            @Index(columnList = "id"),
            @Index(columnList = "appointment_name"),
            @Index(columnList = "identification_code")
        }
)
public class Appointment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "appointment_name")
    private String appointmentName;

    @Setter
    @Column(name = "appointment_place")
    private String appointmentPlace;

    @Setter
    @Column(name = "identification_code")
    private String identificationCode;

    @Setter
    @Column(name = "people_num")
    private int peopleNum;
    
    @Setter
    @Column(name = "vote_deadline")
    private LocalDateTime voteDeadline;
}