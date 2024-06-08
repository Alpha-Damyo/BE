package com.damyo.alpha.api.auth.exception;

import com.damyo.alpha.global.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    ACCOUNT_ALREADY_EXIST(NOT_FOUND, "A101","해당 계정이 이미 존재합니다"),
    ACCOUNT_NOT_FOUND(NOT_FOUND, "A102", "해당 계정이 존재하지 않습니다"),
    EXPIRED_TOKEN(UNAUTHORIZED, "A103", "이미 만료된 토큰입니다"),
    INVALID_TOKEN(UNAUTHORIZED, "A104", "토큰이 유효하지 않습니다"),
    INVALID_PROVIDER(BAD_REQUEST, "A105", "인증 제공자가 올바르지 않습니다."),
    UNKNOWN_ERROR(BAD_REQUEST, "A106", "예상하지 못한 에러입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String exceptionCode;
    private final String message;
}
