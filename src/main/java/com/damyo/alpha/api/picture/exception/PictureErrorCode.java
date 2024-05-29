package com.damyo.alpha.api.picture.exception;

import com.damyo.alpha.global.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum PictureErrorCode implements ErrorCode {
    PICTURE_NOT_FOUND(NOT_FOUND, "P101", "해당 사진을 찾을 수 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String exceptionCode;
    private final String message;
}
