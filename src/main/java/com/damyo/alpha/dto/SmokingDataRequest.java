package com.damyo.alpha.dto;

import com.damyo.alpha.domain.SmokingArea;
import com.damyo.alpha.domain.User;

import java.time.LocalDateTime;

public record SmokingDataRequest(
        User user,
        LocalDateTime creatdAt,
        SmokingArea smokingArea
) {
}
