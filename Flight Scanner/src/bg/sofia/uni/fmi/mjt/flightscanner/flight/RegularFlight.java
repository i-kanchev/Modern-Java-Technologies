package bg.sofia.uni.fmi.mjt.flightscanner.flight;

import bg.sofia.uni.fmi.mjt.flightscanner.airport.Airport;
import bg.sofia.uni.fmi.mjt.flightscanner.exception.FlightCapacityExceededException;
import bg.sofia.uni.fmi.mjt.flightscanner.exception.InvalidFlightException;
import bg.sofia.uni.fmi.mjt.flightscanner.passenger.Passenger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RegularFlight implements Flight {
    private String flightId;
    private Airport airportFrom;
    private Airport airportTo;
    private int totalCapacity;
    private List<Passenger> listOfPassengers;

    private RegularFlight(String flightId, Airport from, Airport to, int totalCapacity) {
        if (flightId == null || flightId.isEmpty() || flightId.trim().isEmpty() ||
            from == null || to == null || totalCapacity < 0) {
            throw new IllegalArgumentException("Invalid data");
        }

        if (from.equals(to)) {
            throw new InvalidFlightException("The destination cannot be the same as the starting point");
        }

        this.flightId = flightId;
        this.airportFrom = from;
        this.airportTo = to;
        this.totalCapacity = totalCapacity;
        this.listOfPassengers = new ArrayList<>();
    }

    public static RegularFlight of(String flightId, Airport from, Airport to, int totalCapacity) {
        return new RegularFlight(flightId, from, to, totalCapacity);
    }

    @Override
    public Airport getFrom() {
        return airportFrom;
    }

    @Override
    public Airport getTo() {
        return airportTo;
    }

    @Override
    public void addPassenger(Passenger passenger) throws FlightCapacityExceededException {
        if (listOfPassengers.size() == totalCapacity) {
            throw new FlightCapacityExceededException("Not enough seats");
        }
        listOfPassengers.add(passenger);
    }

    @Override
    public void addPassengers(Collection<Passenger> passengers) throws FlightCapacityExceededException {
        if (listOfPassengers.size() + passengers.size() > totalCapacity) {
            throw new FlightCapacityExceededException("Not enough seats");
        }
        listOfPassengers.addAll(passengers);
    }

    @Override
    public Collection<Passenger> getAllPassengers() {
        return List.copyOf(listOfPassengers);
    }

    @Override
    public int getFreeSeatsCount() {
        return totalCapacity - listOfPassengers.size();
    }
}
