package com.damyo.alpha.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    USER_NOT_FOUND(NOT_FOUND, 401, "해당 유저를 찾을 수 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final Integer exceptionCode;
    private final String message;
}
