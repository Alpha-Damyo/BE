package com.damyo.alpha.api.picture.exception;

import com.damyo.alpha.global.response.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum S3ErrorCode implements ErrorCode {

    EMPTY_FILE_EXCEPTION(HttpStatus.FORBIDDEN, "P101","File is empty"),
    IO_EXCEPTION_ON_IMAGE_UPLOAD(HttpStatus.FORBIDDEN, "P102", "Failed to upload image"),
    NO_FILE_EXTENSION(HttpStatus.FORBIDDEN, "P103", "No file extension"),
    INVALID_FILE_EXTENSION(HttpStatus.FORBIDDEN, "P104", "Invalid file extension"),
    PUT_OBJECT_EXCEPTION(HttpStatus.FORBIDDEN, "P105", "Failed to put object"),
    IO_EXCEPTION_ON_IMAGE_DELETE(HttpStatus.FORBIDDEN, "P106", "Failed to delete object");

    private final HttpStatus httpStatus;
    private final String exceptionCode;
    private final String message;
}
