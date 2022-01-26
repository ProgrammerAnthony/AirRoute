package com.airroute.demo;

import com.airroute.core.annotation.EnableAirRoute;
import com.airroute.core.model.RouteMapContextFactory;
import com.airroute.core.routemap.FlightInfoContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Anthony
 * @create 2022/1/26
 * @desc
 **/
@Slf4j
@RestController
@SpringBootApplication
@EnableAirRoute("air-route-demo.xml")
public class Application{
    @Autowired
    private RouteMapContextFactory routeMapContextFactory;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/test1")
    public String test1() {
        FlightInfoContext process1 = routeMapContextFactory.getFlightInfoContext("process1");
        process1.loadPackage("id", "process1");
        process1.takeOff();
        return "true";
    }
}