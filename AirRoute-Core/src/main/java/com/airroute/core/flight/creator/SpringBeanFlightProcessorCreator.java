package com.airroute.core.flight.creator;

import com.airroute.core.flight.FlightProcessor;
import com.airroute.core.model.RouteMapContextFactory;
import com.airroute.core.utils.SpringUtil;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

/**
 * @author Anthony
 * @create 2022/1/26
 * @desc
 **/
@Component
public class SpringBeanFlightProcessorCreator implements FlightProcessorCreator{
    @Override
    public FlightProcessor createProcessor(String className, String name) throws Exception {
        Object bean;
        try {
            Class<?> clazz = Class.forName(className);
            //todo init time could not get bean here
            bean = SpringUtil.getBean(clazz);
        } catch (Exception e) {
            return RouteMapContextFactory.DEFAULT_INSTANCE_CREATOR.createProcessor(className, name);
        }
        if (!(bean instanceof FlightProcessor)) {
            throw new IllegalArgumentException("class " + className + " is not instance of FlightProcessor");
        }
        FlightProcessor processor = (FlightProcessor) bean;
        processor.setName(name);
        return (FlightProcessor) bean;
    }
}
