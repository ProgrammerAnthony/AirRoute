package com.airroute.demo.annotation;

import com.airroute.core.flight.ReturnFlightProcessor;
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
public class ReturnFlightProcessorDemo extends ReturnFlightProcessor {
    public ReturnFlightProcessorDemo(){}
    @Override
    protected void onArriveProcess(FlightInfoContext flightInfoContext) {
        System.out.println("ReturnFlightProcessorDemo id: " + flightInfoContext.getPackage("id"));
        System.out.println("ReturnFlightProcessorDemo extra: " +  flightInfoContext.getPackage("extra"));
        int i = 1 / 0;
    }

    @Override
    public void onReturnArriveProcess(FlightInfoContext flightInfoContext) {
        System.out.println("rollback ReturnFlightProcessorDemo " + flightInfoContext.getPackage("id"));
    }
}
