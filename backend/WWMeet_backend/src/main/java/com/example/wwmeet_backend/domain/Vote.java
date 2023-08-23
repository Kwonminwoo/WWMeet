package com.example.wwmeet_backend.domain;

import java.time.LocalDateTime;

public class Vote {
    private Long id;
    private Appointment appointment; // 투표 중인 약속
    private LocalDateTime voteDateStart; // 투표한 시작 시간
    private LocalDateTime voteDateEnd; // 투표한 끝 시간
    // 연속된 시간을 골랐을 때 start ~ end 로 한번에 호출 가능.
}
