package com.damyo.alpha.api.star.exception;

import com.damyo.alpha.global.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;


@Getter
@RequiredArgsConstructor
public enum StarErrorCode implements ErrorCode {

    STAR_NOT_FOUND(NOT_FOUND, "S101", "해당 즐겨찾기를 찾을 수 없습니다."),
    UNAUTHORIZED_ACTION(UNAUTHORIZED, "S102", "즐겨찾기와 로그인의 정보가 맞지 않습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String exceptionCode;
    private final String message;
}
