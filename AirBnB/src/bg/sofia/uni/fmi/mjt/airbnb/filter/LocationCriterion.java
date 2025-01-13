package bg.sofia.uni.fmi.mjt.airbnb.filter;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.Bookable;
import bg.sofia.uni.fmi.mjt.airbnb.accommodation.location.Location;

import java.lang.Math;

public class LocationCriterion implements Criterion {
    private Location currentLocation;
    private double maxDistance;

    public LocationCriterion(Location currentLocation, double maxDistance) {
        this.currentLocation = currentLocation;
        this.maxDistance = maxDistance;
    }

    @Override
    public boolean check(Bookable bookable) {
        if (bookable == null)
            return false;

        return (Math.pow((currentLocation.getX() - bookable.getLocation().getX()), 2) +
                Math.pow((currentLocation.getY() - bookable.getLocation().getY()), 2)
                <= maxDistance * maxDistance);
    }
}
