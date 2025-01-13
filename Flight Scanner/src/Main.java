import bg.sofia.uni.fmi.mjt.flightscanner.FlightScanner;
import bg.sofia.uni.fmi.mjt.flightscanner.airport.Airport;
import bg.sofia.uni.fmi.mjt.flightscanner.flight.Flight;
import bg.sofia.uni.fmi.mjt.flightscanner.flight.RegularFlight;
import bg.sofia.uni.fmi.mjt.flightscanner.passenger.Passenger;

import java.util.List;

public class Main {
    @SuppressWarnings("checkstyle:MagicNumber")
    public static void main(String[] args) {
//        Passenger p1 = new Passenger("P1", "Ivo", Gender.MALE);
//        Passenger p2 = new Passenger("P2", "Niki", Gender.MALE);
//        Passenger p3 = new Passenger("P3", "Gosho", Gender.OTHER);
//        Passenger p4 = new Passenger("P4", "Penka", Gender.FEMALE);
//        Passenger p5 = new Passenger("P5", "Jo", Gender.MALE);
//
//        ArrayList<Passenger> ps = new ArrayList<>();
//        ps.add(p1);
//        ps.add(p2);
//        ps.add(p3);

        Airport a1 = new Airport("A0");
        Airport a2 = new Airport("A1");
        Airport a3 = new Airport("A2");
        Airport a4 = new Airport("A3");
        Airport a5 = new Airport("A4");

        Flight f0 = RegularFlight.of("A", a1, a2, 0);

        Flight f1 = RegularFlight.of("A", a1, a2, 4);
        Flight f2 = RegularFlight.of("B", a1, a3, 6);
        Flight f3 = RegularFlight.of("C", a1, a3, 12);
        Flight f4 = RegularFlight.of("D", a1, a3, 7);
        Flight f5 = RegularFlight.of("E", a1, a3, 6);
        Flight f6 = RegularFlight.of("F", a1, a3, 1);
        Flight f7 = RegularFlight.of("G", a1, a3, 9);
        Flight f8 = RegularFlight.of("H", a1, a3, 8);
//        Flight f3 = RegularFlight.of("C", a2, a1, 12);
//        Flight f4 = RegularFlight.of("D", a4, a2, 7);
//        Flight f5 = RegularFlight.of("E", a4, a5, 6);
//        Flight f6 = RegularFlight.of("F", a3, a2, 1);
//        Flight f7 = RegularFlight.of("G", a3, a4, 9);
//        Flight f8 = RegularFlight.of("H", a3, a5, 8);

//        List<Flight> f = new ArrayList<>();
//        f.add(f1);
//        f.add(f3);
//        f.add(f3);

        FlightScanner test = new FlightScanner();
        test.add(f1);
        test.add(f1);
        test.add(f2);
        test.add(f3);
        test.add(f4);
        test.add(f5);
        test.add(f6);
        test.add(f7);
        test.add(f8);
//        test.addAll(f);

//
//        List<Flight> try1 = test.searchFlights(a1, a5);
        List<Flight> try1 = test.getFlightsSortedByFreeSeats(a1);

        for (Flight f : try1)
        {
            System.out.println(f.getFreeSeatsCount());
        }
//
        System.out.println("Hello world!");
    }
}