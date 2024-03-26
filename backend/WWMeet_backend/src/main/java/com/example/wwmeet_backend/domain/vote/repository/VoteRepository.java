package com.example.wwmeet_backend.domain.vote.repository;

import com.example.wwmeet_backend.domain.participant.entity.Participant;
import com.example.wwmeet_backend.domain.vote.entity.Vote;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query("select v from Vote v where v.participant = :participant")
    Optional<Vote> findByParticipant(@Param("participant") Participant participant);
}
