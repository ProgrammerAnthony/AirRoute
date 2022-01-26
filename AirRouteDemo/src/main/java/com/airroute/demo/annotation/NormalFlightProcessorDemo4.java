package com.airroute.demo.annotation;

import com.airroute.core.flight.NormalFlightProcessor;
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
public class NormalFlightProcessorDemo4 extends NormalFlightProcessor {
    public NormalFlightProcessorDemo4(){}
    @Override
    protected void onArriveProcess(FlightInfoContext flightInfoContext) {
        System.out.println("NormalFlightProcessorDemo4 id: " + flightInfoContext.getPackage("id"));
    }
}
