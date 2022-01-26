package com.airroute.demo.annotation;

import com.airroute.core.flight.DynamicFlightProcessor;
import com.airroute.core.routemap.FlightInfoContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Anthony
 * @create 2022/1/26
 * @desc
 **/
@Slf4j
@Component
public class DynamicFlightProcessorDemo extends DynamicFlightProcessor {
    public DynamicFlightProcessorDemo(){}
    @Override
    protected void onArriveProcess(FlightInfoContext flightInfoContext) {
        System.out.println("DynamicFlightProcessorDemo id: " + flightInfoContext.getPackage("id"));
    }

    @Override
    public String nextStationNodeId(FlightInfoContext flightInfoContext) {
        return "node4";
    }
}
