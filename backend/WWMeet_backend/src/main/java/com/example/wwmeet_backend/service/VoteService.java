package com.example.wwmeet_backend.service;


import com.example.wwmeet_backend.domain.Vote;
import com.example.wwmeet_backend.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class VoteService {
    private final VoteRepository voteRepository;
    public Vote saveVoteSchedule(Vote vote) {
        return voteRepository.save(vote);
    }
}