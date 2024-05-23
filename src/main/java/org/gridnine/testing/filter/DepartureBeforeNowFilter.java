package org.gridnine.testing.filter;

import org.gridnine.testing.Flight;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DepartureBeforeNowFilter implements FlightFilter {
    @Override
    public List<Flight> filter(List<Flight> flights) {
        LocalDateTime now = LocalDateTime.now();
        return flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .noneMatch(segment -> segment.getDepartureDate().isBefore(now)))
                .collect(Collectors.toList());
    }
}
