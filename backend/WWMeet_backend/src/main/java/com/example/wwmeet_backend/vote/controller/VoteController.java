package com.example.wwmeet_backend.vote.controller;


import com.example.wwmeet_backend.vote.dto.SaveVoteRequest;
import com.example.wwmeet_backend.vote.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments/{id}/vote")
public class VoteController {
    private final VoteService voteService;

    @PostMapping
    public Long voteAppointmentSchedule(@PathVariable Long id, @RequestBody SaveVoteRequest saveVoteRequest) {
        return voteService.saveVoteSchedule(id, saveVoteRequest);
    }

}
