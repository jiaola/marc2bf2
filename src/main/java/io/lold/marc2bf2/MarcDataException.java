package io.lold.marc2bf2;

public class MarcDataException extends Exception {
    public MarcDataException(String message) {
        super(message);
    }

    public MarcDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
