package com.damyo.alpha.exception.exception;

import com.damyo.alpha.exception.errorCode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class S3Exception extends RuntimeException{

    private final ErrorCode errorCode;

}
