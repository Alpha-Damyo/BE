package com.damyo.alpha.api.contest.domain;

import java.util.Optional;
import java.util.UUID;

public interface ContestCustomRepository {
    Optional<ContestLike> findByIdAndUserId(UUID userId, Long pictureId);
}
