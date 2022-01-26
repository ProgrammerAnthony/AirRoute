package com.airroute.core.flight;

import com.airroute.core.routemap.FlightInfoContext;

/**
 * @author Anthony
 * @create 2022/1/26
 * @desc a {@link FlightProcessor} to a {@link com.airroute.core.routemap.RouteMap} is a object instance to a class
 **/
public interface FlightProcessor {

    void arriveProcess(FlightInfoContext flightInfoContext);

    void caughtTerroristAttack(FlightInfoContext flightInfoContext, Throwable throwable);

    void setName(String name);

    String getName();
}
