package org.gridnine.testing;

import org.gridnine.testing.filter.ArrivalBeforeDepartureFilter;
import org.gridnine.testing.filter.FlightFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrivalBeforeDepartureFilterTest {

    private FlightFilter filter;

    @BeforeEach
    public void setUp() {
        filter = new ArrivalBeforeDepartureFilter();
    }

    @Test
    public void testFlightsWithNoSegments() {
        List<Flight> flights = Arrays.asList(
                new Flight(Arrays.asList())
        );
        List<Flight> result = filter.filter(flights);
        assertEquals(1, result.size(), "Flights with no segments should be included");
    }

    @Test
    public void testFlightsWithValidSegments() {
        LocalDateTime now = LocalDateTime.now();
        List<Flight> flights = Arrays.asList(
                new Flight(Arrays.asList(
                        new Segment(now, now.plusHours(2)),
                        new Segment(now.plusHours(3), now.plusHours(5))
                ))
        );
        List<Flight> result = filter.filter(flights);
        assertEquals(1, result.size(), "Flights with valid segments should be included");
    }

    @Test
    public void testFlightsWithInvalidSegments() {
        LocalDateTime now = LocalDateTime.now();
        List<Flight> flights = Arrays.asList(
                new Flight(Arrays.asList(
                        new Segment(now, now.minusHours(1))
                ))
        );
        List<Flight> result = filter.filter(flights);
        assertEquals(0, result.size(), "Flights with arrival before departure should be excluded");
    }

    @Test
    public void testMixedFlights() {
        LocalDateTime now = LocalDateTime.now();
        List<Flight> flights = Arrays.asList(
                new Flight(Arrays.asList(
                        new Segment(now, now.plusHours(2)),
                        new Segment(now.plusHours(3), now.plusHours(5))
                )),
                new Flight(Arrays.asList(
                        new Segment(now, now.minusHours(1))
                )),
                new Flight(Arrays.asList(
                        new Segment(now, now.plusHours(1)),
                        new Segment(now.plusHours(2), now.minusHours(1))
                ))
        );
        List<Flight> result = filter.filter(flights);
        assertEquals(1, result.size(), "Only flights with all segments having valid arrival and departure times should be included");
    }
}
