package com.airroute.core.flight.creator;

import com.airroute.core.flight.FlightProcessor;

/**
 * @author Anthony
 * @create 2022/1/26
 * @desc
 **/
public class ReflectFlightProcessorCreator implements FlightProcessorCreator {
    @Override
    public FlightProcessor createProcessor(String className, String name) throws Exception {
        Class<?> clazz = Class.forName(className);
        Object o = clazz.newInstance();
        if (!(o instanceof FlightProcessor)) {
            throw new IllegalArgumentException("class " + className + " is not instance of FlightProcessor");
        }
        FlightProcessor processor = (FlightProcessor) o;
        processor.setName(name);
        return processor;
    }
}
