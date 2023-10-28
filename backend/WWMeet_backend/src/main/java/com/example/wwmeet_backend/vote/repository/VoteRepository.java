package com.example.wwmeet_backend.vote.repository;

import com.example.wwmeet_backend.vote.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
