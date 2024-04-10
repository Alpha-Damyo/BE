package com.damyo.alpha.exception.response;

public record ErrorResponse(
        Integer code,
        String message
) {
}
