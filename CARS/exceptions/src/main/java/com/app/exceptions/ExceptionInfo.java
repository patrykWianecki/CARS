package com.app.exceptions;

import java.time.LocalDateTime;

public class ExceptionInfo {
    private ExceptionCode exceptionCode;
    private String description;
    private LocalDateTime dateTime;

    public ExceptionInfo(ExceptionCode exceptionCode, String description) {
        this.exceptionCode = exceptionCode;
        this.description = description;
        this.dateTime = LocalDateTime.now();
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
