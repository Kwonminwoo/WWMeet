package com.example.wwmeet_backend.participant.repository;

import com.example.wwmeet_backend.participant.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    @Query("insert into participant p ")
    void saveParticipantOfAppointment(String participantName, Long id);
}
