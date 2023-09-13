package com.example.wwmeet_backend.repository;

import com.example.wwmeet_backend.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

}
