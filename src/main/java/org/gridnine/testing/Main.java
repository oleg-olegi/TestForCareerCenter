package org.gridnine.testing;

import org.gridnine.testing.filter.ArrivalBeforeDepartureFilter;
import org.gridnine.testing.filter.DepartureBeforeNowFilter;
import org.gridnine.testing.filter.FlightFilter;
import org.gridnine.testing.filter.GroundTimeExceedsTwoHoursFilter;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();

        FlightFilter arrivalBeforeDepartureFilter = new ArrivalBeforeDepartureFilter();
        FlightFilter departureBeforeNowFilter = new DepartureBeforeNowFilter();
        FlightFilter groundTimeFilter = new GroundTimeExceedsTwoHoursFilter();

        System.out.println("All flights:");
        flights.forEach(System.out::println);

        System.out.println("\nFlights with departure before now");
        departureBeforeNowFilter.filter(flights).forEach(System.out::println);

        System.out.println("\nFlights with arrival before departure");
        arrivalBeforeDepartureFilter.filter(flights).forEach(System.out::println);

        System.out.println("\nFlights with ground time exceeds 2 hours");
        groundTimeFilter.filter(flights).forEach(System.out::println);
    }
}