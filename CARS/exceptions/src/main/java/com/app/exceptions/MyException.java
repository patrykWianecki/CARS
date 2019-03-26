package com.app.exceptions;

public class MyException extends RuntimeException {
    private ExceptionInfo exceptionInfo;

    public MyException(ExceptionCode exceptionCode, String description) {
        this.exceptionInfo = new ExceptionInfo(exceptionCode, description);
    }

    public ExceptionInfo getExceptionInfo() {
        return exceptionInfo;
    }
}
