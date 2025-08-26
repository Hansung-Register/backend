package com.university.register.global.exception;

import com.university.register.global.response.ErrorCode;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private final ErrorCode errorCode;

    @Getter
    private final Object data;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.data = null;
    }

    public ApiException(ErrorCode errorCode, Object data) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.data = data;
    }
}
