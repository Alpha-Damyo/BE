package com.damyo.alpha.global.exception.error;

public record ErrorResponse(
        String code,
        String message
) {
}
