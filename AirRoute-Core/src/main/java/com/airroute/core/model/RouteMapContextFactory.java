package com.airroute.core.model;

import com.airroute.core.flight.creator.FlightProcessorCreator;
import com.airroute.core.flight.creator.ReflectFlightProcessorCreator;
import com.airroute.core.routemap.FlightInfoContext;
import com.airroute.core.routemap.RouteMap;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Anthony
 * @create 2022/1/26
 * @desc
 **/
@Slf4j
public class RouteMapContextFactory {
    public static final FlightProcessorCreator DEFAULT_INSTANCE_CREATOR = new ReflectFlightProcessorCreator();
    private List<RouteMapModel> routeMapModelList;
    private final Map<String, RouteMap> stationNodeDefinitionMap = new ConcurrentHashMap<>();
    private final FlightProcessorCreator flightProcessorCreator;


    public RouteMapContextFactory(List<RouteMapModel> routeMapModelList) throws Exception {
        this(routeMapModelList, DEFAULT_INSTANCE_CREATOR);
    }

    public RouteMapContextFactory(List<RouteMapModel> routeMapModelList, FlightProcessorCreator flightProcessorCreator) throws Exception {
        this.routeMapModelList = routeMapModelList;
        this.flightProcessorCreator = flightProcessorCreator;
        init();
    }

    private void init() throws Exception {
        //check all route maps
        for (RouteMapModel routeMapModel : routeMapModelList) {
            routeMapModel.checkStationValidity();
        }
        //build route map for latter use
        for (RouteMapModel routeMapModel : routeMapModelList) {
            RouteMap routeMap = routeMapModel.buildRouteMap(flightProcessorCreator);
            log.info("build route map success: {}", routeMap.toString());
            stationNodeDefinitionMap.put(routeMap.getName(),routeMap);
        }
    }

    public FlightInfoContext getFlightInfoContext(String name){
        RouteMap routeMap=stationNodeDefinitionMap.get(name);
        if(routeMap==null){
            throw new IllegalArgumentException("route map not available");
        }
        return new FlightInfoContext(routeMap);
    }
}
