package com.example.wwmeet_android.domain;

import java.time.LocalDateTime;

public class Appointment {
    private Long id;
    private String code; // 약속 코드
    private String name; // 약속 이름
    private int numberPeople; // 약속 인원수
    private String local; // 약속 장소
    private LocalDateTime appointDate; // 약속 날짜
    private LocalDateTime limitDate; // 투표 기한

    public Appointment() {
    }

    public Appointment(String code, String name, int numberPeople, String local, LocalDateTime appointDate, LocalDateTime limitDate) {
        this.code = code;
        this.name = name;
        this.numberPeople = numberPeople;
        this.local = local;
        this.appointDate = appointDate;
        this.limitDate = limitDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberPeople() {
        return numberPeople;
    }

    public void setNumberPeople(int numberPeople) {
        this.numberPeople = numberPeople;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public LocalDateTime getAppointDate() {
        return appointDate;
    }

    public void setAppointDate(LocalDateTime appointDate) {
        this.appointDate = appointDate;
    }

    public LocalDateTime getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(LocalDateTime limitDate) {
        this.limitDate = limitDate;
    }
}
