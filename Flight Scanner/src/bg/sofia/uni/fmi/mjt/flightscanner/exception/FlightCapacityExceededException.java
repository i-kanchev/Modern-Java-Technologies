package bg.sofia.uni.fmi.mjt.flightscanner.exception;

public class FlightCapacityExceededException extends ArrayIndexOutOfBoundsException {
    public FlightCapacityExceededException(String errorMessage) {
        super(errorMessage);
    }
}
