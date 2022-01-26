package com.airroute.core.annotation;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Anthony
 * @create 2022/1/26
 * @desc
 **/
@Configuration
@ComponentScan(value = {"com.airroute.core.flight.creator"})
public class ComponentScanConfig {
}
