package com.damyo.alpha.api.challenge.controller;

import com.damyo.alpha.api.challenge.controller.dto.ChallengeResponse;
import com.damyo.alpha.api.challenge.service.ChallengeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ch")
@Tag(name = "ChallengeController")
public class ChallengeController {
    private final ChallengeService challengeService;

    @PostMapping("/getAllChallenge")
    public List<ChallengeResponse> getAllChallenge() {
        return challengeService.getAllChallenge();
    }
    @PostMapping("/getCurrentChallenge")
    public List<ChallengeResponse> getCurrentChallenge() {
        return challengeService.getCurrentChallenge();
    }
}
