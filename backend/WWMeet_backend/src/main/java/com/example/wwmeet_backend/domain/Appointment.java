package com.example.wwmeet_backend.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(
        indexes = {
            @Index(columnList = "id"),
            @Index(columnList = "appointment_name"),
            @Index(columnList = "appointment_code")
        }
)
public class Appointment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "appointment_name")
    private String appointmentName; // 약속 이름

    @Setter
    @Column(name = "appointment_place")
    private String appointmentPlace; // 약속 장소

    @Setter
    @Column(name = "appointment_code")
    private String appointmentCode; // 코드 번호

    @Setter
    @Column(name = "people_num")
    private int peopleNum; // 약속 인원
    
    @Setter
    @Column(name = "appointment_date")
    private LocalDateTime appointmentDate; // 약속 날짜

    @OneToMany(mappedBy = "appointment")
    private List<Vote> voteList;
}
