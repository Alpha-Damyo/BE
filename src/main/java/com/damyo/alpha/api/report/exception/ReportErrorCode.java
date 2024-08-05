package com.damyo.alpha.api.report.exception;

import com.damyo.alpha.global.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReportErrorCode implements ErrorCode {
    ALREADY_REPORT(HttpStatus.BAD_REQUEST, "R101", "이미 신고한 흡연구역 입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String exceptionCode;
    private final String message;
}
