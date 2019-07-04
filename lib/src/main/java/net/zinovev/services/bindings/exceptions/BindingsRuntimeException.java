package net.zinovev.services.bindings.exceptions;

public class BindingsRuntimeException extends RuntimeException {

    public BindingsRuntimeException() {
    }

    public BindingsRuntimeException(String message) {
        super(message);
    }

    public BindingsRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BindingsRuntimeException(Throwable cause) {
        super(cause);
    }

    public BindingsRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
