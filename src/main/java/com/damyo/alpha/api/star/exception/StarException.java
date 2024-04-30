package com.damyo.alpha.api.star.exception;

import com.damyo.alpha.global.exception.error.BaseException;
import com.damyo.alpha.global.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class StarException extends BaseException {

    public StarException(ErrorCode errorCode) {
        super(errorCode);
    }
}
