package org.inspirerobotics.sumobots;

public class SumobotsRuntimeException extends RuntimeException{

    public SumobotsRuntimeException() {
    }

    public SumobotsRuntimeException(String message) {
        super(message);
    }

    public SumobotsRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SumobotsRuntimeException(Throwable cause) {
        super(cause);
    }

    public SumobotsRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
