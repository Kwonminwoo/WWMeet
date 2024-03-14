package com.example.wwmeet_backend.domain.place.domain;

import com.example.wwmeet_backend.domain.participant.domain.Participant;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "participant_id")
    private Participant participant;

    private String address;

    private double latitude;

    private double longitude;

    @Builder
    public Place(Participant participant, String address, double latitude, double longitude) {
        this.participant = participant;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
