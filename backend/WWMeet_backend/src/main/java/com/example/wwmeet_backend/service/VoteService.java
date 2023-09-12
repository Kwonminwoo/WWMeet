package com.example.wwmeet_backend.service;


import com.example.wwmeet_backend.domain.Vote;
import com.example.wwmeet_backend.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoteService {
    private final VoteRepository voteRepository;

    public Vote saveVoteSchedule(Vote vote) {
        return null;
    }
}
