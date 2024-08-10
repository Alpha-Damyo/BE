package com.damyo.alpha.api.info.controller.dto;

import com.damyo.alpha.api.info.domain.Info;
import com.damyo.alpha.api.smokingarea.domain.SmokingArea;
import com.damyo.alpha.api.user.domain.User;
import lombok.NonNull;

public record UpdateInfoRequest(
        @NonNull
        String smokingAreaId,
        @NonNull
        Integer score
) {
        public Info toEntity(SmokingArea smokingArea, User user) {
                return Info.builder().
                        smokingArea(smokingArea).
                        user(user).
                        score(score).
                        build();
        }
}
