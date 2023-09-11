package com.example.wwmeet_backend.service;

import com.example.wwmeet_backend.domain.Participant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantService {
    private final ParticipantRepository participantRepository;

}
