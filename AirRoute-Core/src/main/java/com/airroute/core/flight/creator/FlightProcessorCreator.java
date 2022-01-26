package com.airroute.core.flight.creator;

import com.airroute.core.flight.FlightProcessor;

/**
 * @author Anthony
 * @create 2022/1/26
 * @desc
 **/
public interface FlightProcessorCreator {
    FlightProcessor createProcessor(String className, String name) throws Exception;
}
