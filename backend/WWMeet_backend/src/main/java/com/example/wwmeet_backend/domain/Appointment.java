package com.example.wwmeet_backend.domain;

import java.time.LocalDateTime;

public class Appointment {
    private Long id;
    private String name; // 약속 이름
    private String place; // 약속 장소
    private String code; // 방 코드 번호
    private int peopleNum; // 약속 인원
    private LocalDateTime date; // 약속 날짜
}
