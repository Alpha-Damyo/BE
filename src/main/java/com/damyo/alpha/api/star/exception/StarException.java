package com.damyo.alpha.api.star.exception;

import com.damyo.alpha.global.response.exception.error.BaseException;
import com.damyo.alpha.global.response.exception.error.ErrorCode;

public class StarException extends BaseException {

    public StarException(ErrorCode errorCode) {
        super(errorCode);
    }
}
