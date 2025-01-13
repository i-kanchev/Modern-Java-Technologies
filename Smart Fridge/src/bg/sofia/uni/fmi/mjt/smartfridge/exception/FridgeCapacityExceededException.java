package bg.sofia.uni.fmi.mjt.smartfridge.exception;

public class FridgeCapacityExceededException extends ArrayIndexOutOfBoundsException {
    public FridgeCapacityExceededException(String errorMessage) {
        super(errorMessage);
    }
}
