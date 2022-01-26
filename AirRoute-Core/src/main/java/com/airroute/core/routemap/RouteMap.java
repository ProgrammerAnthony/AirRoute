package com.airroute.core.routemap;

import com.airroute.core.utils.ProcessorUtils;
import lombok.Getter;

/**
 * @author Anthony
 * @create 2022/1/26
 * @desc
 **/
@Getter
public class RouteMap {
    private String name;

    private StationNodeDefinition from;

    public RouteMap() {
    }

    public RouteMap(StationNodeDefinition from) {
        setFrom(from);
    }

    public void setFrom(StationNodeDefinition from) {
        this.from = from;
        if (ProcessorUtils.hasRing(from)) {
            throw new IllegalArgumentException("Processor chain exists ring.");
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        buildStr(sb, from);
        return sb.toString();
    }

    private void buildStr(StringBuilder sb, StationNodeDefinition node) {
        for (StationNodeDefinition stationNodeDefinition : node.getNextStationNodes().values()) {
            sb.append(node.getName()).append(" -> ").append(stationNodeDefinition.getName()).append("\n");
            buildStr(sb, stationNodeDefinition);
        }
    }

}
