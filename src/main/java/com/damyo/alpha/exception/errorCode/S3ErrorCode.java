package com.damyo.alpha.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum S3ErrorCode implements ErrorCode{

    EMPTY_FILE_EXCEPTION(HttpStatus.FORBIDDEN, 301,"File is empty"),
    IO_EXCEPTION_ON_IMAGE_UPLOAD(HttpStatus.FORBIDDEN, 302, "Failed to upload image"),
    NO_FILE_EXTENSION(HttpStatus.FORBIDDEN, 303, "No file extension"),
    INVALID_FILE_EXTENSION(HttpStatus.FORBIDDEN, 304, "Invalid file extension"),
    PUT_OBJECT_EXCEPTION(HttpStatus.FORBIDDEN, 305, "Failed to put object"),
    IO_EXCEPTION_ON_IMAGE_DELETE(HttpStatus.FORBIDDEN, 306, "Failed to delete object");

    private final HttpStatus httpStatus;
    private final Integer exceptionCode;
    private final String message;
}
