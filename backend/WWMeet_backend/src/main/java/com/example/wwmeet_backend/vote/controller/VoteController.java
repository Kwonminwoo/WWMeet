package com.example.wwmeet_backend.vote.controller;


import com.example.wwmeet_backend.vote.dto.SaveVoteRequest;
import com.example.wwmeet_backend.vote.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class VoteController {
    private final VoteService voteService;

    @PostMapping("/appointments/{id}/vote")
    public Long voteAppointmentSchedule(@PathVariable Long id, @RequestBody SaveVoteRequest saveVoteRequest) {
        System.out.println(saveVoteRequest.getPossibleScheduleList().get(0).getStartDateTime());
        return voteService.saveVoteSchedule(id, saveVoteRequest);
    }
}
