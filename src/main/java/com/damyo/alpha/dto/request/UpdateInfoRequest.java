package com.damyo.alpha.dto.request;

import com.damyo.alpha.domain.Info;
import com.damyo.alpha.domain.SmokingArea;
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
        Boolean chair
) {
        public Info toEntity(SmokingArea smokingArea) {
                return Info.builder().
                        smokingArea(smokingArea).
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
