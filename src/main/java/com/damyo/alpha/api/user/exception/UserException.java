package com.damyo.alpha.api.user.exception;


import com.damyo.alpha.global.response.exception.error.BaseException;
import com.damyo.alpha.global.response.exception.error.ErrorCode;

public class UserException extends BaseException {
    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
