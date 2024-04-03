package com.damyo.alpha.dto.response;

public record InfoResponse(
        Integer size,
        float score,
        Integer opened,
        Integer closed,
        Integer notExist,
        Integer airOut,
        Integer hygiene,
        Integer dirty,
        Integer indoor,
        Integer outdoor,
        Integer big,
        Integer small,
        Integer crowded,
        Integer quite,
        Integer chair
) {
}
