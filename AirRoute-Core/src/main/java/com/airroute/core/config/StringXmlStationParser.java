package com.airroute.core.config;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.StringReader;

/**
 * @author Anthony
 * @create 2022/1/26
 * @desc
 **/
public class StringXmlStationParser extends XmlStationParser {
    private String xmlConfig;
    public StringXmlStationParser(String xmlConfig){
        this.xmlConfig = xmlConfig;
    }
    @Override
    protected Document getDocument() throws DocumentException {
        SAXReader saxReader = new SAXReader();
        StringReader reader = new StringReader(xmlConfig);
        return saxReader.read(reader);
    }
}
