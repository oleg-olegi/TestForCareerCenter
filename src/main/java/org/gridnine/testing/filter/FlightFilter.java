package org.gridnine.testing.filter;

import org.gridnine.testing.Flight;

import java.util.List;

public interface FlightFilter {
    List<Flight> filter(List<Flight> flights);
}
