package com.example.wwmeet_backend.participant.repository;

import com.example.wwmeet_backend.participant.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

}
