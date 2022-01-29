package com.airroute.core.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anthony
 * @create 2022/1/26
 * @desc annotation to enable the ability of AirRoute Process Engine
 **/
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({StationNodeRegistrar.class, ComponentScanConfig.class})
public @interface EnableAirRoute {
    String value();
}
