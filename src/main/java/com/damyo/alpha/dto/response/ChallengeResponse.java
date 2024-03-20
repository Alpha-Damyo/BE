package com.damyo.alpha.dto.response;

import com.damyo.alpha.domain.Challenge;
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
