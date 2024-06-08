package com.damyo.alpha.global.exception.handler;

import com.damyo.alpha.global.exception.error.BaseException;
import com.damyo.alpha.global.exception.error.CommonErrorCode;
import com.damyo.alpha.global.exception.error.ErrorCode;
import com.damyo.alpha.api.auth.exception.AuthException;
import com.damyo.alpha.global.exception.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getHttpStatus()).body(new ErrorResponse(errorCode.getExceptionCode(), errorCode.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalException(Exception e) {
        log.error(e.getClass() + " : " + e.getMessage());
        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(errorCode.getHttpStatus()).body(new ErrorResponse(errorCode.getExceptionCode(), errorCode.getMessage()));
    }

}
