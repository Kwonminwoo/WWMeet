package com.example.wwmeet_backend.repository;

import com.example.wwmeet_backend.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
