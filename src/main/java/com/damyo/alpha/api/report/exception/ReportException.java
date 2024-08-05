package com.damyo.alpha.api.report.exception;

import com.damyo.alpha.global.exception.error.BaseException;
import com.damyo.alpha.global.exception.error.ErrorCode;

public class ReportException extends BaseException {
    public ReportException(ErrorCode errorCode) {
        super(errorCode);
    }
}
