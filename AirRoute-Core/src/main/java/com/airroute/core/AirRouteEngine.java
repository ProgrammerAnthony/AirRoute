package com.airroute.core;

import com.airroute.core.config.StationParser;
import com.airroute.core.model.RouteMapContextFactory;
import com.airroute.core.routemap.FlightInfoContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AirRouteEngine {

    private final RouteMapContextFactory factory;

    public AirRouteEngine(StationParser stationParser) throws Exception {
        this.factory = new RouteMapContextFactory(stationParser.parse());
    }


    public FlightInfoContext getContext(String name) {
        return factory.getFlightInfoContext(name);
    }
}
