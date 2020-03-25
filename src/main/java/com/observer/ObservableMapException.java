package com.observer;

public class ObservableMapException extends RuntimeException {
    public ObservableMapException() {
    }

    public ObservableMapException(String message) {
        super(message);
    }

    public ObservableMapException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObservableMapException(Throwable cause) {
        super(cause);
    }

    public ObservableMapException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
