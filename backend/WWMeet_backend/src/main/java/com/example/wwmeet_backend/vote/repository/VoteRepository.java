package com.example.wwmeet_backend.vote.repository;

import com.example.wwmeet_backend.participant.domain.Participant;
import com.example.wwmeet_backend.vote.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    @Query("select v from Vote v where v.participant = :participant")
    Optional<Vote> findByParticipant(@Param("participant") Participant participant);
}
