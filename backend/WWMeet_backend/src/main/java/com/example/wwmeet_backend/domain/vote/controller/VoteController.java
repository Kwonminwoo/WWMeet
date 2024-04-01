package com.example.wwmeet_backend.domain.vote.controller;


import com.example.wwmeet_backend.domain.vote.dto.SaveVoteRequest;
import com.example.wwmeet_backend.domain.vote.service.VoteService;
import com.example.wwmeet_backend.global.response.ResponseAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseAPI> voteAppointmentSchedule(@PathVariable Long id,
        @RequestBody SaveVoteRequest saveVoteRequest) {
        return ResponseEntity.ok(ResponseAPI.response("투표 성공",
            voteService.saveVoteSchedule(id, saveVoteRequest)));
    }

}
