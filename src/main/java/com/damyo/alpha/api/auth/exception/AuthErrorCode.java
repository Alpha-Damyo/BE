package com.damyo.alpha.api.auth.exception;

import com.damyo.alpha.global.response.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    ACCOUNT_ALREADY_EXIST(NOT_FOUND, "A101","해당 계정이 이미 존재합니다"),
    ACCOUNT_NOT_FOUND(NOT_FOUND, "A102", "해당 계정이 존재하지 않습니다"),
    EXPIRED_TOKEN(UNAUTHORIZED, "A103", "이미 만료된 접근 토큰입니다"),
    INVALID_TOKEN(UNAUTHORIZED, "A104", "접튼 토큰이 유효하지 않습니다"),
    INVALID_PROVIDER(BAD_REQUEST, "A105", "인증 제공자가 올바르지 않습니다."),
    FAIL_GET_INFO(BAD_REQUEST, "A106", "OAuth 토큰이 잘못되었거나 만료되었습니다."),
    UNKNOWN_ERROR(BAD_REQUEST, "A107", "예상하지 못한 에러입니다."),
    INVALID_REFRESH_TOKEN(UNAUTHORIZED, "A108", "갱신 토큰이 유효하지 않습니다. 다시 로그인을 해주세요")
    ;

    private final HttpStatus httpStatus;
    private final String exceptionCode;
    private final String message;
}
