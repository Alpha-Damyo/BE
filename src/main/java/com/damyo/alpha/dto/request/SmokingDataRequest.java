package com.damyo.alpha.dto.request;

import com.damyo.alpha.domain.SmokingArea;
import com.damyo.alpha.domain.User;

import java.time.LocalDateTime;
import java.util.UUID;

public record SmokingDataRequest(
        String email,
        LocalDateTime creatdAt,
        SmokingArea smokingArea
) {
}
