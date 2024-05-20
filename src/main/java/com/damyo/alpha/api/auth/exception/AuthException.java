package com.damyo.alpha.api.auth.exception;

import com.damyo.alpha.global.exception.error.BaseException;
import com.damyo.alpha.global.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


public class AuthException extends BaseException {
    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
