package com.damyo.alpha.api.auth.exception;

import com.damyo.alpha.global.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    EMAIL_ALREADY_EXIST(NOT_FOUND, 1001,"해당 이메일이 이미 존재합니다"),
    EMAIL_NOT_FOUND(NOT_FOUND, 1002, "해당 이메일이 존재하지 않습니다"),
    EXPIRED_TOKEN(UNAUTHORIZED, 1003, "이미 만료된 토큰입니다"),
    INVALID_TOKEN(UNAUTHORIZED, 1004, "토큰이 유효하지 않습니다")
    ;

    private final HttpStatus httpStatus;
    private final Integer exceptionCode;
    private final String message;
}
