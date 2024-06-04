package com.damyo.alpha.api.info.controller.dto;

public record InfoResponse(
        Integer size,
        Float score,
        Long opened,
        Long closed,
        Long notExist,
        Long airOut,
        Long hygiene,
        Long dirty,
        Long indoor,
        Long outdoor,
        Long big,
        Long small,
        Long crowded,
        Long quite,
        Long chair
) {
}
