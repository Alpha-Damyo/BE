package com.damyo.alpha.global.exception.error;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String name();
    HttpStatus getHttpStatus();
    String getMessage();
    String getExceptionCode();
}
