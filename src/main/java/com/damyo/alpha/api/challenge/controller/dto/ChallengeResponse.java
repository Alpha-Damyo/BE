package com.damyo.alpha.api.challenge.controller.dto;

import com.damyo.alpha.api.challenge.domain.Challenge;
import lombok.NonNull;

import java.time.LocalDateTime;

public record ChallengeResponse (
    @NonNull
    String name,
    @NonNull
    LocalDateTime startTime,
    @NonNull
    LocalDateTime endTime,
    @NonNull
    String bannerImgUrl,
    @NonNull
    String detailImgUrl
) {
    public ChallengeResponse(Challenge challenge) {
        this(challenge.getName(), challenge.getStartTime(), challenge.getEndTime(), challenge.getBannerImgUrl(), challenge.getDetailImgUrl());
    }
}
