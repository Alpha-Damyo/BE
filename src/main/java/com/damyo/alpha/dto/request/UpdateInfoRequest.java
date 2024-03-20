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
        boolean dirty
) {
        public UpdateInfoRequest(Info info) {
                this(info.getSmokingArea().getId(),
                        info.getScore(),
                        info.isOpened(),
                        info.isClosed(),
                        info.isNotExist(),
                        info.isAirOut(),
                        info.isHygiene(),
                        info.isDirty());
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
                        build();
        }
}
