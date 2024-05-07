package com.damyo.alpha.api.info.controller.dto;

public record InfoResponse(
        Integer size,
        Float score,
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