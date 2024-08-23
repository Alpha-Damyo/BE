package com.damyo.alpha.api.picture.exception;

import com.damyo.alpha.global.response.exception.error.BaseException;
import com.damyo.alpha.global.response.exception.error.ErrorCode;

public class PictureException extends BaseException {
    public PictureException(ErrorCode errorCode) {
        super(errorCode);
    }
}
