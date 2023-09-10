package com.example.wwmeet_backend.controller;


import com.example.wwmeet_backend.service.VoteService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteService;
}