package com.damyo.alpha.dto.response;

public record InfoResponse(
        float score,
        int opened,
        int closed,
        int notExist,
        int airOut,
        int hygiene,
        int dirty
) {
    public InfoResponse(float score,
                        int opened,
                        int closed,
                        int notExist,
                        int airOut,
                        int hygiene,
                        int dirty) {
        this.score = score;
        this.opened = opened;
        this.closed = closed;
        this.notExist = notExist;
        this.airOut = airOut;
        this.hygiene = hygiene;
        this.dirty = dirty;
    }
}
