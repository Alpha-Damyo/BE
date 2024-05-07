package com.damyo.alpha.api.smokingdata.controller.dto;

import com.damyo.alpha.api.smokingarea.domain.SmokingArea;
import com.damyo.alpha.api.user.domain.User;

import java.time.LocalDateTime;

public record SmokingDataRequest(
        User user,
        LocalDateTime createdAt,
        String smokingAreaId
) {
}
