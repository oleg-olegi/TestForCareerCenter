package org.gridnine.testing;

import org.gridnine.testing.filter.FlightFilter;
import org.gridnine.testing.filter.GroundTimeExceedsTwoHoursFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GroundTimeExceedsTeoHoursFilterTest {
    private FlightFilter filter;

    @BeforeEach
    public void setUp() {
        filter = new GroundTimeExceedsTwoHoursFilter();
    }

    @Test
    public void testFlightsWithNoSegments() {
        List<Flight> flights = List.of(
                new Flight(new ArrayList<>())
        );
        List<Flight> result = filter.filter(flights);
        assertEquals(1, result.size(), "Flights with no segments should be included");
    }

    @Test
    public void testFlightsWithSingleSegment() {
        List<Flight> flights = List.of(
                new Flight(List.of(
                        new Segment(LocalDateTime.now(), LocalDateTime.now().plusHours(1))
                ))
        );
        List<Flight> result = filter.filter(flights);
        assertEquals(1, result.size(), "Flights with single segment should be included");
    }

    @Test
    public void testFlightsWithGroundTimeLessThanTwoHours() {
        List<Flight> flights = List.of(
                new Flight(Arrays.asList(
                        new Segment(LocalDateTime.now(), LocalDateTime.now().plusHours(1)),
                        new Segment(LocalDateTime.now().plusHours(1).plusMinutes(30), LocalDateTime.now().plusHours(3))
                ))
        );
        List<Flight> result = filter.filter(flights);
        assertEquals(1, result.size(), "Flights with ground time less than two hours should be included");
    }

    @Test
    public void testFlightsWithGroundTimeMoreThanTwoHours() {
        List<Flight> flights = List.of(
                new Flight(Arrays.asList(
                        new Segment(LocalDateTime.now(), LocalDateTime.now().plusHours(1)),
                        new Segment(LocalDateTime.now().plusHours(4), LocalDateTime.now().plusHours(5))
                ))
        );
        List<Flight> result = filter.filter(flights);
        assertEquals(0, result.size(), "Flights with ground time more than two hours should be excluded");
    }

    @Test
    public void testMixedFlights() {
        List<Flight> flights = Arrays.asList(
                new Flight(Arrays.asList(
                        new Segment(LocalDateTime.now(), LocalDateTime.now().plusHours(1)),
                        new Segment(LocalDateTime.now().plusHours(1).plusMinutes(30), LocalDateTime.now().plusHours(3))
                )),
                new Flight(Arrays.asList(
                        new Segment(LocalDateTime.now(), LocalDateTime.now().plusHours(1)),
                        new Segment(LocalDateTime.now().plusHours(4), LocalDateTime.now().plusHours(5))
                )),
                new Flight(Arrays.asList(
                        new Segment(LocalDateTime.now(), LocalDateTime.now().plusHours(1)),
                        new Segment(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(3))
                ))
        );
        List<Flight> result = filter.filter(flights);
        assertEquals(2, result.size(), "Only flights with ground time less than or equal to two hours should be included");
    }
}

