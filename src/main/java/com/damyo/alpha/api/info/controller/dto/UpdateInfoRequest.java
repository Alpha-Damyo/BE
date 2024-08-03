package com.damyo.alpha.api.info.controller.dto;

import com.damyo.alpha.api.info.domain.Info;
import com.damyo.alpha.api.smokingarea.domain.SmokingArea;
import com.damyo.alpha.api.user.domain.User;
import lombok.NonNull;

public record UpdateInfoRequest(
        @NonNull
        String smokingAreaId,
        @NonNull
        Integer score,
        Boolean opened,
        Boolean closed,
        Boolean indoor,
        Boolean outdoor,
        String url
) {
        public Info toEntity(SmokingArea smokingArea, User user) {
                return Info.builder().
                        smokingArea(smokingArea).
                        user(user).
                        score(score).
                        opened(opened).
                        closed(closed).
                        indoor(indoor).
                        outdoor(outdoor).
                        build();
        }
}
