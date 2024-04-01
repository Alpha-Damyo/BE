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
                        info.getOpened(),
                        info.getClosed(),
                        info.getNotExist(),
                        info.getAirOut(),
                        info.getHygiene(),
                        info.getDirty(),
                        info.getIndoor(),
                        info.getOutdoor(),
                        info.getBig(),
                        info.getSmall(),
                        info.getCrowded(),
                        info.getQuite(),
                        info.getChair());
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
