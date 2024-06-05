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
        Boolean notExist,
        Boolean airOut,
        Boolean hygiene,
        Boolean dirty,
        Boolean indoor,
        Boolean outdoor,
        Boolean big,
        Boolean small,
        Boolean crowded,
        Boolean quite,
        Boolean chair,
        String url
) {
        public Info toEntity(SmokingArea smokingArea, User user) {
                return Info.builder().
                        smokingArea(smokingArea).
                        user(user).
                        score(score).
                        opened(opened).
                        closed(closed).
                        notExist(notExist).
                        airOut(airOut).
                        hygiene(hygiene).
                        dirty(dirty).
                        indoor(indoor).
                        outdoor(outdoor).
                        big(big).
                        small(small).
                        crowded(crowded).
                        quite(quite).
                        chair(chair).
                        build();
        }
}
