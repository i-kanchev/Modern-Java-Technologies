package bg.sofia.uni.fmi.mjt.flightscanner.passenger;

public record Passenger(String id, String name, Gender gender) {
    public Passenger(String id, String name, Gender gender) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.id = id;
        this.name = name;
        this.gender = gender;
    }
}
