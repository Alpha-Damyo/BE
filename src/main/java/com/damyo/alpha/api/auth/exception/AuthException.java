package com.damyo.alpha.api.auth.exception;

import com.damyo.alpha.global.response.exception.error.BaseException;
import com.damyo.alpha.global.response.exception.error.ErrorCode;


public class AuthException extends BaseException {
    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
