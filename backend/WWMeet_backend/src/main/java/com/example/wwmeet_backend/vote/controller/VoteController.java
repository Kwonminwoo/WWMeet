package com.example.wwmeet_backend.vote.controller;


import com.example.wwmeet_backend.vote.dto.SaveVoteRequest;
import com.example.wwmeet_backend.vote.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class VoteController {
    private final VoteService voteService;

    @PostMapping("/appointment/vote")
    public Long voteAppointmentSchedule(@RequestBody SaveVoteRequest saveVoteRequest) {
        return voteService.saveVoteSchedule(saveVoteRequest);
    }
}
