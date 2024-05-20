package com.damyo.alpha.api.user.exception;


import com.damyo.alpha.global.exception.error.BaseException;
import com.damyo.alpha.global.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class UserException extends BaseException {
    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
