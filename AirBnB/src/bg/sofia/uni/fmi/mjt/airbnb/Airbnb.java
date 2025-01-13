package bg.sofia.uni.fmi.mjt.airbnb;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.Bookable;
import bg.sofia.uni.fmi.mjt.airbnb.filter.Criterion;

public class Airbnb implements AirbnbAPI {
    private Bookable[] accommodations;

    public Airbnb(Bookable[] accommodations) {
        this.accommodations = new Bookable[accommodations.length];
        for (int i = 0; i < accommodations.length; i++) {
            this.accommodations[i] = accommodations[i];
        }
    }

    @Override
    public Bookable findAccommodationById(String id) {
        for (Bookable booking : accommodations) {
            if (booking.getId().equalsIgnoreCase(id))
                return booking;
        }
        return null;
    }

    @Override
    public double estimateTotalRevenue() {
        int totalRevenue = 0;
        for (Bookable booking : accommodations) {
            totalRevenue += booking.getTotalPriceOfStay();
        }
        return totalRevenue;
    }

    @Override
    public long countBookings() {
        int countBookings = 0;
        for (Bookable booking : accommodations) {
            if (booking.isBooked())
                countBookings++;
        }
        return countBookings;
    }

    @Override
    public Bookable[] filterAccommodations(Criterion... criteria) {
        int criterionMetAccommodations = 0;
        for (Bookable booking : accommodations) {
            boolean criterionMet = true;
            for (Criterion currCriteria : criteria) {
                if (!currCriteria.check(booking)) {
                    criterionMet = false;
                    break;
                }
            }
            if (criterionMet)
                criterionMetAccommodations++;
        }

        Bookable[] filteredAccommodations = new Bookable[criterionMetAccommodations];
        int index = 0;

        for (Bookable booking : accommodations) {
            boolean criterionMet = true;
            for (Criterion currCriteria : criteria) {
                if (!currCriteria.check(booking)) {
                    criterionMet = false;
                    break;
                }
            }
            if (criterionMet)
                filteredAccommodations[index++] = booking;
        }

        return filteredAccommodations;
    }
}
