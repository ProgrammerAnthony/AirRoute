package com.airroute.core.model;

import com.airroute.core.enums.InvokeMethod;
import lombok.Data;


/**
 * @author Anthony
 * @create 2022/1/26
 * @desc
 **/
@Data
public class StationNodeModel {
    private String name;
    private String className;
    private String nextStationNode;
    private Boolean begin = false;
    private InvokeMethod invokeMethod;
}
