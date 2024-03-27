package com.damyo.alpha.exception.exception;

import com.damyo.alpha.exception.errorCode.ErrorCode;

public class AreaException extends RuntimeException{
    private final ErrorCode errorCode;

    public AreaException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode(){
        return errorCode;
    }
}
