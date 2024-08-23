package com.damyo.alpha.api.user.exception;

import com.damyo.alpha.global.response.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    USER_NOT_FOUND(NOT_FOUND, "U101", "해당 유저를 찾을 수 없습니다."),
    INVALID_NAME(BAD_REQUEST, "U102", "변경하려는 이름이 기존과 동일합니다.")
    ;

    private final HttpStatus httpStatus;
    private final String exceptionCode;
    private final String message;
}
