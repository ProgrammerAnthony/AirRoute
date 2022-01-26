package com.airroute.core.flight;

import com.airroute.core.routemap.FlightInfoContext;

/**
 * @author Anthony
 * @create 2022/1/26
 * @desc
 **/
public abstract class AbstractFlightProcessor implements FlightProcessor {
    private String name;

    @Override
    public void arriveProcess(FlightInfoContext flightInfoContext) {
        beforeArriveProcess(flightInfoContext);
        onArriveProcess(flightInfoContext);
        onLeaveProcess(flightInfoContext);
    }

    /**
     * before process operation
     * @param flightInfoContext
     */
    protected void beforeArriveProcess(FlightInfoContext flightInfoContext) {

    }

    /**
     * core process operation
     * @param flightInfoContext
     */
    protected abstract void onArriveProcess(FlightInfoContext flightInfoContext);

    /**
     * after process operation
     * @param flightInfoContext
     */
    protected void onLeaveProcess(FlightInfoContext flightInfoContext) {
        // default no-op
    }

    /**
     * exception caught here
     * @param flightInfoContext
     * @param throwable
     */
    @Override
    public void caughtTerroristAttack(FlightInfoContext flightInfoContext, Throwable throwable) {

    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
