package com.airroute.core.model;

import com.airroute.core.flight.creator.FlightProcessorCreator;
import com.airroute.core.flight.FlightProcessor;
import com.airroute.core.routemap.RouteMap;
import com.airroute.core.routemap.StationNodeDefinition;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Anthony
 * @create 2022/1/26
 * @desc {@link RouteMapModel} contains many {@link StationNodeModel} nodes
 **/
@Data
public class RouteMapModel{
    public String name;
    public Map<String, StationNodeModel> nodes = new HashMap<>();

    public void addNode(StationNodeModel stationNodeModel) {
        if (nodes.containsKey(stationNodeModel.getName())) {
            throw new IllegalArgumentException("replicate station name in one route map is not supported");
        }
        nodes.put(stationNodeModel.getName(), stationNodeModel);
    }

    public void checkStationValidity() {
        int startNode = 0;
        for (StationNodeModel stationNodeModel : nodes.values()) {
            String className = stationNodeModel.getClassName();
            try {
                Class.forName(className);
            } catch (Throwable e) {
                throw new IllegalArgumentException("could not load class from [" + stationNodeModel.getName() + "]：" + className);
            }
            String nextStationNode = stationNodeModel.getNextStationNode();
            if (nextStationNode != null) {
                String[] nextNodeNames = nextStationNode.split(",");
                for (String nodeName : nextNodeNames) {
                    if (!nodes.containsKey(nodeName)) {
                        throw new IllegalArgumentException("station node with name [" + nodeName + "] does not exist");
                    }
                }
            }
            if (stationNodeModel.getBegin()) {
                startNode++;
            }
        }
        if (startNode != 1) {
            throw new IllegalArgumentException("invalid station node config， only one start node supported");
        }
    }

    public RouteMap buildRouteMap(FlightProcessorCreator creator) throws Exception {
        Map<String, StationNodeDefinition> stationNodeMap = new HashMap<>();
        RouteMap routeMap = new RouteMap();
        routeMap.setName(name);
        //convert station node model into station node definition
        for (StationNodeModel stationNodeModel : nodes.values()) {
            String className = stationNodeModel.getClassName();
            FlightProcessor processor = creator.createProcessor(className, stationNodeModel.getName());
            StationNodeDefinition stationNodeDefinition = new StationNodeDefinition();
            stationNodeDefinition.setFlightProcessor(processor);
            stationNodeDefinition.setName(stationNodeModel.getName());
            //only put first station node into RouteMap
            if (stationNodeModel.getBegin()) {
                routeMap.setFrom(stationNodeDefinition);
            }
            stationNodeDefinition.setInvokeMethod(stationNodeModel.getInvokeMethod());
            stationNodeMap.put(stationNodeModel.getName(), stationNodeDefinition);
        }
        //build connection of all station node
        for (StationNodeDefinition stationNodeDefinition : stationNodeMap.values()) {
            String nextStationNode = nodes.get(stationNodeDefinition.getName()).getNextStationNode();
            if (StringUtils.isEmpty(nextStationNode)) {
                continue;
            }
            //multi nextNodes supported
            String[] nextNodes = nextStationNode.split(",");
            for (String nextNode : nextNodes) {
                stationNodeDefinition.addNextTransferNode(stationNodeMap.get(nextNode));
            }
        }
        return routeMap;
    }
}
