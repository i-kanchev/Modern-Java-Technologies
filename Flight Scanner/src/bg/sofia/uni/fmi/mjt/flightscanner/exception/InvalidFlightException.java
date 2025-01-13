package bg.sofia.uni.fmi.mjt.flightscanner.exception;

public class InvalidFlightException extends RuntimeException {
    public InvalidFlightException(String errorMessage) {
        super(errorMessage);
    }
}
