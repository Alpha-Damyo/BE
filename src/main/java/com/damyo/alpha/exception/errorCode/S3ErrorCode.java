package com.damyo.alpha.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum S3ErrorCode implements ErrorCode{

    EMPTY_FILE_EXCEPTION(HttpStatus.FORBIDDEN, "File is empty"),
    IO_EXCEPTION_ON_IMAGE_UPLOAD(HttpStatus.FORBIDDEN, "Failed to upload image"),
    NO_FILE_EXTENSION(HttpStatus.FORBIDDEN, "No file extension"),
    INVALID_FILE_EXTENSION(HttpStatus.FORBIDDEN, "Invalid file extension"),
    PUT_OBJECT_EXCEPTION(HttpStatus.FORBIDDEN, "Failed to put object"),
    IO_EXCEPTION_ON_IMAGE_DELETE(HttpStatus.FORBIDDEN, "Failed to delete object");

    private final HttpStatus httpStatus;
    private final String message;
}
