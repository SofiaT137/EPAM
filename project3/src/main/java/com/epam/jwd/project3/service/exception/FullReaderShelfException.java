package com.epam.jwd.project3.service.exception;

public class FullReaderShelfException extends Exception{

    public FullReaderShelfException() {
    }

    public FullReaderShelfException(String message) {
        super(message);
    }

    public FullReaderShelfException(String message, Throwable cause) {
        super(message, cause);
    }

    public FullReaderShelfException(Throwable cause) {
        super(cause);
    }

    public FullReaderShelfException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
