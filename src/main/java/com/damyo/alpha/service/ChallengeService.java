package com.damyo.alpha.service;

import com.damyo.alpha.domain.Challenge;
import com.damyo.alpha.dto.response.ChallengeResponse;
import com.damyo.alpha.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ChallengeService {
    private final ChallengeRepository challengeRepository;

    public List<ChallengeResponse> getCurrentChallenge() {
        List<Challenge> challenges = challengeRepository.findAllByCurrentTime(LocalDateTime.now());
        List<ChallengeResponse> challengeResponses = new ArrayList<>();
        for (Challenge challenge : challenges) {
            challengeResponses.add(challenge.toDto());
        }
        return challengeResponses;
    }
    public List<ChallengeResponse> getAllChallenge() {
        List<Challenge> challenges = challengeRepository.findAll();
        List<ChallengeResponse> challengeResponses = new ArrayList<>();
        for (Challenge challenge : challenges) {
            challengeResponses.add(challenge.toDto());
        }
        return challengeResponses;
    }
}
