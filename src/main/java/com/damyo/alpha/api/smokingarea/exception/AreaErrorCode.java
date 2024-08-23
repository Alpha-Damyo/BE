package com.damyo.alpha.api.smokingarea.exception;


import com.damyo.alpha.global.response.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AreaErrorCode implements ErrorCode {

    NOT_FOUND_ID(HttpStatus.NOT_FOUND, "SA101", "요청하신 ID의 흡연구역을 찾을 수 없습니다."),
    ALREADY_REPORT(HttpStatus.BAD_REQUEST, "SA102", "이미 신고한 흡연구역 입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String exceptionCode;
    private final String message;
}
