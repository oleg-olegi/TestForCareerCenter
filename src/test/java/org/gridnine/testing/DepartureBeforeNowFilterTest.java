package org.gridnine.testing;

import org.gridnine.testing.filter.DepartureBeforeNowFilter;
import org.gridnine.testing.filter.FlightFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepartureBeforeNowFilterTest {
    private FlightFilter filter;

    @BeforeEach
    public void setUp() {
        filter = new DepartureBeforeNowFilter();
    }

    @Test
    public void testFlightsWithNoSegments() {
        List<Flight> flights = List.of(
                new Flight(List.of())
        );
        List<Flight> result = filter.filter(flights);
        assertEquals(1, result.size(), "Flights with no segments should be included");
    }

    @Test
    public void testFlightsWithDepartureBeforeNow() {
        LocalDateTime now = LocalDateTime.now();
        List<Flight> flights = Arrays.asList(
                new Flight(Arrays.asList(
                        new Segment(now.minusHours(1), now.plusHours(2))
                ))
        );
        List<Flight> result = filter.filter(flights);
        assertEquals(0, result.size(), "Flights with departure before now should be excluded");
    }

    @Test
    public void testFlightsWithDepartureAfterNow() {
        LocalDateTime now = LocalDateTime.now();
        List<Flight> flights = Arrays.asList(
                new Flight(Arrays.asList(
                        new Segment(now.plusHours(1), now.plusHours(2))
                ))
        );
        List<Flight> result = filter.filter(flights);
        assertEquals(1, result.size(), "Flights with departure after now should be included");
    }

    @Test
    public void testMixedFlights() {
        LocalDateTime now = LocalDateTime.now();
        List<Flight> flights = Arrays.asList(
                new Flight(Arrays.asList(
                        new Segment(now.minusHours(1), now.plusHours(2))
                )),
                new Flight(Arrays.asList(
                        new Segment(now.plusHours(1), now.plusHours(2))
                )),
                new Flight(Arrays.asList(
                        new Segment(now.minusHours(2), now.minusHours(1)),
                        new Segment(now.plusHours(1), now.plusHours(2))
                ))
        );
        List<Flight> result = filter.filter(flights);
        assertEquals(1, result.size(), "Only flights with all segments departing after now should be included");
    }
}
