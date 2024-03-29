package com.damyo.alpha.exception.errorCode;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AreaErrorCode implements ErrorCode{

    NOT_FOUND_ID(HttpStatus.NOT_FOUND, 1001, "요청하신 ID의 흡연구역을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final Integer exceptionCode;
    private final String message;
}
