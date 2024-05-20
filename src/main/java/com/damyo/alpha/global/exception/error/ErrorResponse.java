package com.damyo.alpha.global.exception.error;

public record ErrorResponse(
        Integer code,
        String message
) {
}
