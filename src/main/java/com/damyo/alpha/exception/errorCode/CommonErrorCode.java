package com.damyo.alpha.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode{

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, 100,"Invalid parameter included"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, 100, "Resource not exists"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 100,"Internal server error"),
    ;

    private final HttpStatus httpStatus;
    private final Integer exceptionCode;
    private final String message;
}
