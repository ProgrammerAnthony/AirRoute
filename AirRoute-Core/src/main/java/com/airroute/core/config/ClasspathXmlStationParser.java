package com.airroute.core.config;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * @author Anthony
 * @create 2022/1/26
 * @desc
 **/
public class ClasspathXmlStationParser extends XmlStationParser {
    private final String file;

    public ClasspathXmlStationParser(String file){
        this.file = file.startsWith("/") ? file : "/" + file;
    }
    @Override
    protected Document getDocument() throws DocumentException {
        InputStream resourceAsStream = getClass().getResourceAsStream(file);
        SAXReader saxReader = new SAXReader();
        return saxReader.read(resourceAsStream);
    }
}
