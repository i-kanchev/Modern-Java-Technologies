package bg.sofia.uni.fmi.mjt.smartfridge.exception;

public class InsufficientQuantityException extends RuntimeException {
    public InsufficientQuantityException(String errorMessage) {
        super(errorMessage);
    }
}
