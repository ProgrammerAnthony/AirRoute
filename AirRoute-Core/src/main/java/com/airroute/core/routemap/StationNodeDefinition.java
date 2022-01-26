package com.airroute.core.routemap;

import com.airroute.core.enums.InvokeMethod;
import com.airroute.core.flight.DynamicFlightProcessor;
import com.airroute.core.flight.FlightProcessor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Anthony
 * @create 2022/1/26
 * @desc
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StationNodeDefinition {
    private String name;

    private FlightProcessor flightProcessor;

    private Map<String, StationNodeDefinition> nextStationNodes = new HashMap<>();

    private InvokeMethod invokeMethod = InvokeMethod.SYNC;

    public Map<String, StationNodeDefinition> getNextStationNodes() {
        return nextStationNodes;
    }
    private boolean hasSyncNextNode = false;

    public void addNextTransferNode(StationNodeDefinition stationNodeDefinition){
        if(stationNodeDefinition.getName().equals(name)){
            throw new IllegalArgumentException("Duplicate station Node name: " + name);
        }
        if (nextStationNodes.containsKey(stationNodeDefinition.getName())) {
            throw new IllegalArgumentException("station Node[name=" + name + "] already contains next node which id is " + stationNodeDefinition.getName());
        }

        boolean isSync = InvokeMethod.SYNC.equals(stationNodeDefinition.invokeMethod);
        boolean isDynamic = flightProcessor instanceof DynamicFlightProcessor;
        if (!isDynamic && hasSyncNextNode && isSync) {
            throw new IllegalArgumentException("only one sync next node is supported");
        }
        if (isSync) {
            hasSyncNextNode = true;
        }
        nextStationNodes.put(stationNodeDefinition.getName(), stationNodeDefinition);
    }
}
