package com.damyo.alpha.api.challenge.controller;

import com.damyo.alpha.api.challenge.controller.dto.ChallengeResponse;
import com.damyo.alpha.api.challenge.service.ChallengeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary="챌린지 목록 받기", description = "모든 챌린지 목록을 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "챌린지 목록 반환에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    public List<ChallengeResponse> getAllChallenge() {
        return challengeService.getAllChallenge();
    }

    @PostMapping("/getCurrentChallenge")
    @Operation(summary="현재 진행 중인 챌린지 목록 받기", description = "진행 중인 챌린지 목록을 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "챌린지 목록 반환에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    public List<ChallengeResponse> getCurrentChallenge() {
        return challengeService.getCurrentChallenge();
    }
}
