package com.damyo.alpha.dto.request;

import com.damyo.alpha.domain.Info;
import com.damyo.alpha.domain.SmokingArea;
import lombok.NonNull;

public record UpdateInfoRequest(
        @NonNull
        String smokingAreaId,
        @NonNull
        int score,
        boolean opened,
        boolean closed,
        boolean notExist,
        boolean airOut,
        boolean hygiene,
        boolean dirty,
        boolean indoor,
        boolean outdoor,
        boolean big,
        boolean small,
        boolean crowded,
        boolean quite,
        boolean chair
) {
        public UpdateInfoRequest(Info info) {
                this(info.getSmokingArea().getId(),
                        info.getScore(),
                        info.isOpened(),
                        info.isClosed(),
                        info.isNotExist(),
                        info.isAirOut(),
                        info.isHygiene(),
                        info.isDirty(),
                        info.isIndoor(),
                        info.isOutdoor(),
                        info.isBig(),
                        info.isSmall(),
                        info.isCrowded(),
                        info.isQuite(),
                        info.isChair());
        }
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
