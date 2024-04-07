package com.damyo.alpha.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    EMAIL_ALREADY_EXIST(HttpStatus.NOT_FOUND, 101,"해당 이메일이 이미 존재합니다"),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, 102, "해당 이메일이 존재하지 않습니다"),
    SIGNATURE_NOT_FOUND(HttpStatus.UNAUTHORIZED, 102, "서명을 확인하지 못했습니다"),
    SIGNATURE_INVALID(HttpStatus.UNAUTHORIZED, 103, "서명이 올바르지 않습니다."),
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, 104, "토큰의 길이 및 형식이 올바르지 않습니다"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 105, "이미 만료된 토큰입니다"),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, 106, "지원되지 않는 토큰입니다"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 107, "토큰이 유효하지 않습니다"),
    ;

    private final HttpStatus httpStatus;
    private final Integer exceptionCode;
    private final String message;
}
