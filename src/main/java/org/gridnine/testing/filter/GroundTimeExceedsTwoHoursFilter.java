package org.gridnine.testing.filter;

import org.gridnine.testing.Flight;
import org.gridnine.testing.Segment;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class GroundTimeExceedsTwoHoursFilter implements FlightFilter {
    @Override
    public List<Flight> filter(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> {
                    List<Segment> segments = flight.getSegments();
                    for (int i = 0; i < segments.size() - 1; i++) {
                        Segment currentSegment = segments.get(i);
                        Segment nextSegment = segments.get(i + 1);
                        Duration groundTime = Duration.between(currentSegment.getArrivalDate(),
                                nextSegment.getDepartureDate());
                        if (groundTime.toHours() >= 2) {
                            return false;
                        }
                    }
                    return true;
                }).collect(Collectors.toList());
    }
}
