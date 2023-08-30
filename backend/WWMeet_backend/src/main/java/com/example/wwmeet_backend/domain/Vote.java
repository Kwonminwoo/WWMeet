package com.example.wwmeet_backend.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
        indexes = {
                @Index(columnList = "id"),
                @Index(columnList = "appointment_id"),
                @Index(columnList = "vote_date_start"),
                @Index(columnList = "vote_date_end"),
        }
)
public class Vote {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment; // 투표 중인 약속

    @Setter
    @Column(name = "vote_date_start")
    private LocalDateTime voteDateStart; // 투표한 시작 시간

    @Setter
    @Column(name = "vote_date_end")
    private LocalDateTime voteDateEnd; // 투표한 끝 시간
    // 연속된 시간을 골랐을 때 start ~ end 로 한번에 호출 가능.
}
