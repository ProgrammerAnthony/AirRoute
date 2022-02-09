package com.airroute.core.config;

import com.airroute.core.enums.InvokeMethod;
import com.airroute.core.model.RouteMapModel;
import com.airroute.core.model.StationNodeModel;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anthony
 * @create 2022/1/26
 * @desc parse xml into {@link RouteMapModel} list ,which contains {@link StationNodeModel}
 **/
public abstract class XmlStationParser implements StationParser {
    @Override
    public List<RouteMapModel> parse() throws Exception {
        Document document = getDocument();
        Element rootElement = document.getRootElement();
        List<Element> processElements = rootElement.elements();
        List<RouteMapModel> routeMapModels = new ArrayList<>();
        for (Element process : processElements) {
            RouteMapModel model = new RouteMapModel();
            model.setName(process.attributeValue("name"));
            List<Element> elements = process.elements();
            for (Element node : elements) {
                StationNodeModel stationNodeModel = new StationNodeModel();
                stationNodeModel.setName(node.attributeValue("name"));
                stationNodeModel.setClassName(node.attributeValue("class"));
                String next = node.attributeValue("next");
                if (next != null) {
                    stationNodeModel.setNextStationNode(next);
                }
                String begin = node.attributeValue("begin");
                stationNodeModel.setBegin(Boolean.parseBoolean(begin));
                String invokeMethodStr = node.attributeValue("invoke-method");
                InvokeMethod invokeMethod = invokeMethodStr == null ? InvokeMethod.SYNC :
                        InvokeMethod.valueOf(invokeMethodStr.toUpperCase());
                stationNodeModel.setInvokeMethod(invokeMethod);
                model.addNode(stationNodeModel);
            }
            routeMapModels.add(model);
        }
        return routeMapModels;
    }

    protected abstract Document getDocument() throws DocumentException;
}
