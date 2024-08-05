package com.damyo.alpha.api.info.controller.dto;

public record InfoResponse(
        Integer size,
        Float score,
        Long opened,
        Long closed,
        Long indoor,
        Long outdoor
) {
}
