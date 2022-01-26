package com.airroute.core.routemap;

import com.airroute.core.enums.InvokeMethod;
import com.airroute.core.flight.DynamicFlightProcessor;
import com.airroute.core.flight.FlightProcessor;
import com.airroute.core.flight.ReturnFlightProcessor;
import com.airroute.core.utils.ProcessorUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author Anthony
 * @create 2022/1/26
 * @desc
 **/
@Slf4j
public class FlightInfoContext {
    /**
     * params load into context
     */
    private final Map<String, Object> packages = new HashMap<>();

    private final RouteMap routeMap;

    private final Stack<ReturnFlightProcessor> returnFlightProcessorStack = new Stack<>();

    public FlightInfoContext(RouteMap routeMap) {
        this.routeMap = routeMap;
    }

    public void loadPackage(String key, Object value) {
        packages.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T getPackage(String key) {
        return (T) packages.get(key);
    }

    /**
     * start process
     */
    public void takeOff() {
        process(routeMap.getFrom());
    }

    private void process(StationNodeDefinition stationNode) {
        if (stationNode == null) {
            return;
        }
        FlightProcessor flightProcessor = stationNode.getFlightProcessor();
        try {
            if (flightProcessor instanceof ReturnFlightProcessor) {
                returnFlightProcessorStack.push((ReturnFlightProcessor) flightProcessor);
            }
            flightProcessor.arriveProcess(this);
        } catch (Exception e) {
            ReturnFlightProcessor returnFlightProcessor;
            while (!returnFlightProcessorStack.empty()) {
                returnFlightProcessor = returnFlightProcessorStack.pop();
                try {
                    returnFlightProcessor.onReturnArriveProcess(this);
                } catch (Exception e1) {
                    log.error("rollback error, process={},context={}", returnFlightProcessor.getName(), packages.values());
                }
            }
            flightProcessor.caughtTerroristAttack(this, e);
            throw e;
        }
        //recursively call next node's arriveProcess
        StationNodeDefinition nextNode = null;
        Map<String, StationNodeDefinition> nextStationNodeMap = stationNode.getNextStationNodes();
        if (flightProcessor instanceof DynamicFlightProcessor) {
            DynamicFlightProcessor dynamicFlightProcessor = (DynamicFlightProcessor) flightProcessor;
            String nextStationNodeId = dynamicFlightProcessor.nextStationNodeId(this);
            if (!nextStationNodeMap.containsKey(nextStationNodeId)) {
                throw new IllegalArgumentException("DynamicFlightProcessor can not find next node with id:" + nextStationNodeId);
            }
            nextNode=nextStationNodeMap.get(nextStationNodeId);
        } else {
            //only one next node
            if(!nextStationNodeMap.isEmpty()){
                nextNode=nextStationNodeMap.values().stream().findAny().get();
            }
        }
        InvokeMethod invokeMethod=stationNode.getInvokeMethod();
        if(invokeMethod.equals(InvokeMethod.SYNC)){
            process(nextNode);
        }else{
            StationNodeDefinition finalNextNode=nextNode;
            ProcessorUtils.executeAsync(()->process(finalNextNode));
        }
    }
}
