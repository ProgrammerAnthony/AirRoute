package com.airroute.core.config;

import com.airroute.core.model.RouteMapModel;

import java.util.List;

/**
 * @author Anthony
 * @create 2022/1/26
 * @desc
 **/
public interface StationParser {
    List<RouteMapModel> parse() throws Exception;
}
