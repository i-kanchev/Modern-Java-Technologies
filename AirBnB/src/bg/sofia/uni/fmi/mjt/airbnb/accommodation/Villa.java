package bg.sofia.uni.fmi.mjt.airbnb.accommodation;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.location.Location;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Villa implements Bookable {
    private static int currentId = 0;
    private String id;
    private Location location;
    private double pricePerNight;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;

    public Villa(Location location, double pricePerNight) {
        id = "VIL-" + currentId++;
        this.location = location;
        this.pricePerNight = pricePerNight;
        checkInDate = null;
        checkOutDate = null;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public boolean isBooked() {
        if (checkInDate == null || checkOutDate == null)
            return false;
        return true;
    }

    @Override
    public boolean book(LocalDateTime checkIn, LocalDateTime checkOut) {
        if (checkInDate != null || checkOutDate != null)
            return false;
        if (checkIn == null || checkOut == null)
            return false;
        if (checkIn.equals(checkOut))
            return false;
        if (checkIn.isBefore(LocalDateTime.now()))
            return false;
        if (checkOut.isBefore(checkIn))
            return false;

        checkInDate = checkIn;
        checkOutDate = checkOut;

        return true;
    }

    @Override
    public double getTotalPriceOfStay() {
        if (checkInDate == null || checkOutDate == null)
            return 0.0;
        return (checkInDate.until(checkOutDate, ChronoUnit.DAYS)) * pricePerNight;
    }

    @Override
    public double getPricePerNight() {
        return pricePerNight;
    }
}
