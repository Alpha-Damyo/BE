package com.damyo.alpha.api.smokingarea.exception;

import com.damyo.alpha.global.exception.error.BaseException;
import com.damyo.alpha.global.exception.error.ErrorCode;

public class AreaException extends BaseException {
    public AreaException(ErrorCode errorCode) {
        super(errorCode);
    }
}
