package com.damyo.alpha.api.challenge.controller;

import com.damyo.alpha.api.challenge.controller.dto.ChallengeResponse;
import com.damyo.alpha.api.challenge.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChallengeController {
    private final ChallengeService challengeService;

    @PostMapping("/ch/getAllChallenge")
    public List<ChallengeResponse> getAllChallenge() {
        return challengeService.getAllChallenge();
    }
    @PostMapping("ch/getCurrentChallenge")
    public List<ChallengeResponse> getCurrentChallenge() {
        return challengeService.getCurrentChallenge();
    }
}
