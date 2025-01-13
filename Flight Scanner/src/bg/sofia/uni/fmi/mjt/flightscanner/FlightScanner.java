package bg.sofia.uni.fmi.mjt.flightscanner;

import bg.sofia.uni.fmi.mjt.flightscanner.airport.Airport;
import bg.sofia.uni.fmi.mjt.flightscanner.comparators.FlightByDestinationComparator;
import bg.sofia.uni.fmi.mjt.flightscanner.comparators.FlightByFreeSeatsDescComparator;
import bg.sofia.uni.fmi.mjt.flightscanner.exception.InvalidFlightException;
import bg.sofia.uni.fmi.mjt.flightscanner.flight.Flight;

import java.util.*;

public class FlightScanner implements FlightScannerAPI {
    private Set unique;
    private List<Flight> listOfFlights;

    public FlightScanner() {
        this.unique = new HashSet<>();
        this.listOfFlights = new ArrayList<>();
    }

    @Override
    public void add(Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("Invalid flight");
        }

        if (unique.contains(flight)) {
            return;
        }

        unique.add(flight);
        listOfFlights.add(flight);
    }

    @Override
    public void addAll(Collection<Flight> flights) {
        for (Flight flight : flights) {
            add(flight);
        }
    }

    @Override
    public List<Flight> searchFlights(Airport from, Airport to) {
        if (from == null || to == null || from.equals(to)) {
            throw new IllegalArgumentException("Incorrect data");
        }

        List<String> mapping = mapAirports();

        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < mapping.size(); i++) {
            graph.add(new ArrayList<>());
        }

        for (Flight flight : listOfFlights) {
            graph.get(mapping.indexOf(flight.getFrom().id()))
                .add(mapping.indexOf(flight.getTo().id()));
        }

        int source = mapping.indexOf(from.id());
        int destination = mapping.indexOf(to.id());

        List<Integer> simplePath = findPath(graph, source, destination, mapping.size());

        List<Flight> path = convertPath(simplePath, mapping);

        return path;
    }

    @Override
    public List<Flight> getFlightsSortedByFreeSeats(Airport from) {
        if (from == null) {
            throw new IllegalArgumentException("Incorrect data");
        }

        List<Flight> listOfFlightsByCriterion = new ArrayList<>();
        for (Flight flight : listOfFlights) {
            if (flight.getFrom().equals(from)) {
                listOfFlightsByCriterion.add(flight);
            }
        }

        listOfFlightsByCriterion.sort(new FlightByFreeSeatsDescComparator());

        return List.copyOf(listOfFlightsByCriterion);
    }

    @Override
    public List<Flight> getFlightsSortedByDestination(Airport from) {
        if (from == null) {
            throw new IllegalArgumentException("Incorrect data");
        }

        List<Flight> listOfFlightsByCriterion = new ArrayList<>();
        for (Flight flight : listOfFlights) {
            if (flight.getFrom().equals(from)) {
                listOfFlightsByCriterion.add(flight);
            }
        }

        listOfFlightsByCriterion.sort(new FlightByDestinationComparator());

        return List.copyOf(listOfFlightsByCriterion);
    }

    private List<String> mapAirports() {
        Set<Airport> uniqueAirports = new HashSet<>();
        List<String> mapping = new ArrayList<>();

        for (Flight flight : listOfFlights) {
            if (!uniqueAirports.contains(flight.getFrom())) {
                uniqueAirports.add(flight.getFrom());
                mapping.add(flight.getFrom().id());
            }
            if (!uniqueAirports.contains(flight.getTo())) {
                uniqueAirports.add(flight.getTo());
                mapping.add(flight.getTo().id());
            }
        }

        return mapping;
    }

    private List<Integer> findPath(List<List<Integer>> graph, int src, int dst, int v) {
        boolean[] isVisited = new boolean[v];
        Queue<List<Integer>> queue = new LinkedList<>();
        List<Integer> path = new ArrayList<>();
        path.add(src);
        queue.offer(path);

        while (!queue.isEmpty()) {
            path = queue.poll();
            int last = path.get(path.size() - 1);
            isVisited[last] = true;

            if (last == dst) {
                return path;
            }

            List<Integer> lastNode = graph.get(last);
            for (int i = 0; i < lastNode.size(); i++) {
                if (!isVisited[lastNode.get(i)]) {
                    isVisited[last] = true;
                    List<Integer> newpath = new ArrayList<>(path);
                    newpath.add(lastNode.get(i));
                    queue.offer(newpath);
                }
            }
        }

        return null;
    }

    private List<Flight> convertPath(List<Integer> simplePath, List<String> mapping) {
        if (simplePath == null) {
            return new ArrayList<>();
        }

        List<Flight> path = new ArrayList<>();

        for (int i = 0; i < simplePath.size() - 1; i++) {
            for (Flight flight : listOfFlights) {
                if (simplePath.get(i) == mapping.indexOf(flight.getFrom().id()) &&
                    simplePath.get(i + 1) == mapping.indexOf(flight.getTo().id())) {
                    path.add(flight);
                    break;
                }
            }
        }

        return path;
    }
}
