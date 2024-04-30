package com.damyo.alpha.api.picture.exception;

import com.damyo.alpha.global.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class S3Exception extends RuntimeException{

    private final ErrorCode errorCode;

}
