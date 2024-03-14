package com.example.wwmeet_backend.domain.vote.controller;


import com.example.wwmeet_backend.domain.vote.dto.SaveVoteRequest;
import com.example.wwmeet_backend.domain.vote.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments/{id}/vote")
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public Long voteAppointmentSchedule(@PathVariable Long id,
        @RequestBody SaveVoteRequest saveVoteRequest) {
        return voteService.saveVoteSchedule(id, saveVoteRequest);
    }

}
