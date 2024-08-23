package com.damyo.alpha.api.smokingarea.exception;

import com.damyo.alpha.global.response.exception.error.BaseException;
import com.damyo.alpha.global.response.exception.error.ErrorCode;

public class AreaException extends BaseException {
    public AreaException(ErrorCode errorCode) {
        super(errorCode);
    }
}
