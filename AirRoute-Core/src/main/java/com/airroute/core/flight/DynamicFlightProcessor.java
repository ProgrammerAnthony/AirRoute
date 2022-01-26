package com.airroute.core.flight;

import com.airroute.core.routemap.FlightInfoContext;

/**
 * @author Anthony
 * @create 2022/1/26
 * @desc processor dynamically choose next station node
 **/
public abstract class DynamicFlightProcessor extends AbstractFlightProcessor {

    /**
     * @param flightInfoContext
     * @return
     */
    public abstract String nextStationNodeId(FlightInfoContext flightInfoContext);
}
