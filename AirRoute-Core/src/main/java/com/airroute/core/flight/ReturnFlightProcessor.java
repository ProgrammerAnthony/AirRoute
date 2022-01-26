package com.airroute.core.flight;

import com.airroute.core.routemap.FlightInfoContext;

/**
 * @author Anthony
 * @create 2022/1/26
 * @desc processor which extends this class can do rollback operation in {@link #onReturnArriveProcess(FlightInfoContext)}
 **/
public abstract class ReturnFlightProcessor extends AbstractFlightProcessor {

    /**
     * rollback operation here
     *
     * @param flightInfoContext
     */
     public abstract void onReturnArriveProcess(FlightInfoContext flightInfoContext);
}
