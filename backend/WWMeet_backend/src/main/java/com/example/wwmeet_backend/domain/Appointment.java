package com.example.wwmeet_backend.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
        indexes = {
            @Index(columnList = "id"),
            @Index(columnList = "name"),
            @Index(columnList = "code")
        }
)
public class Appointment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String name; // 약속 이름

    @Setter
    private String place; // 약속 장소

    @Setter
    private String code; // 방 코드 번호

    @Setter
    @Column(name = "people_num")
    private int peopleNum; // 약속 인원
    
    @Setter
    private LocalDateTime date; // 약속 날짜

    @OneToMany(mappedBy = "appointment")
    private List<Vote> voteList;
}
