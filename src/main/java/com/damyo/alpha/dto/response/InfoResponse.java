package com.damyo.alpha.dto.response;

public record InfoResponse(
        float score,
        int opened,
        int closed,
        int notExist,
        int airOut,
        int hygiene,
        int dirty,
        int indoor,
        int outdoor,
        int big,
        int small,
        int crowded,
        int quite,
        int chair
) {
    public InfoResponse(float score,
                        int opened,
                        int closed,
                        int notExist,
                        int airOut,
                        int hygiene,
                        int dirty,
                        int indoor,
                        int outdoor,
                        int big,
                        int small,
                        int crowded,
                        int quite,
                        int chair) {
        this.score = score;
        this.opened = opened;
        this.closed = closed;
        this.notExist = notExist;
        this.airOut = airOut;
        this.hygiene = hygiene;
        this.dirty = dirty;
        this.indoor = indoor;
        this.outdoor = outdoor;
        this.big = big;
        this.small = small;
        this.crowded = crowded;
        this.quite = quite;
        this.chair = chair;
    }
}
