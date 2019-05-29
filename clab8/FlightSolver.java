import java.util.ArrayList;
import java.util.Comparator;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are >= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver {
    private ArrayList<Flight> flights;

    public FlightSolver(ArrayList<Flight> flights) {
        this.flights = flights;
    }

    public int solve() {
        // Create comparator for sorting by lambda expression
        Comparator<Flight> startTimeComparator = Comparator.comparing(Flight::startTime);
        Comparator<Flight> endTimeComparator = Comparator.comparing(Flight::endTime);

        // Create new lists and copy for sorting
        ArrayList<Flight> sortByStart = new ArrayList<>();
        ArrayList<Flight> sortByEnd = new ArrayList<>();
        for (Flight f : flights) {
            sortByStart.add(f);
            sortByEnd.add(f);
        }

        // Sorting
        sortByStart.sort(startTimeComparator);
        sortByEnd.sort(endTimeComparator);

        int n = flights.size();
        // i : counter for start, j : counter for end
        // Assume first flight already take-off
        int i = 1, j = 0;
        int maxPassenger = sortByStart.remove(0).passengers();
        int passenger = maxPassenger;

        // Loop until all flights take-off and land
        while (i < n && j < n) {
            if (sortByStart.get(0).startTime() <= sortByEnd.get(0).endTime()) {
                passenger += sortByStart.remove(0).passengers();
                if (passenger > maxPassenger) {
                    maxPassenger = passenger;
                }
                i += 1;
            } else {
                passenger -= sortByEnd.remove(0).passengers();
                j += 1;
            }
        }
        return maxPassenger;
    }

    /* For test use
    private static ArrayList<Flight> makeFlights(int[] startTimes, int[] endTimes, int[] passengerCounts) {
        ArrayList<Flight> flights = new ArrayList<>();
        for (int i = 0; i < startTimes.length; i++) {
            flights.add(new Flight(startTimes[i], endTimes[i], passengerCounts[i]));
        }
        return flights;
    }

    public static void main(String[] args) {
        int[] start = {13, 28, 29, 14, 40, 17, 3 };
        int[] end = {107, 95, 111, 105, 70, 127, 74};
        int[] pass = {1, 1, 1, 1, 1, 1, 1};
        FlightSolver solver = new FlightSolver(makeFlights(start, end, pass));
        System.out.println(solver.solve());
    }
    */
}
