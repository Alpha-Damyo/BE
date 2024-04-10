package com.damyo.alpha.exception.handler;

import com.damyo.alpha.exception.errorCode.ErrorCode;
import com.damyo.alpha.exception.exception.AuthException;
import com.damyo.alpha.exception.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> handleUserException(AuthException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getHttpStatus()).body(new ErrorResponse(errorCode.getExceptionCode(), errorCode.getMessage()));
    }

}
