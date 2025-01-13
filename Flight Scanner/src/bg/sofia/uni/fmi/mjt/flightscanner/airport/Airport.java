package bg.sofia.uni.fmi.mjt.flightscanner.airport;

public record Airport(String id) {
    public Airport(String id) {
        if (id == null || id.isEmpty() || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid id");
        }
        this.id = id;
    }
}
