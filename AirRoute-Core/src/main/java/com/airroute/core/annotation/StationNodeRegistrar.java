package com.airroute.core.annotation;

import com.airroute.core.config.ClasspathXmlStationParser;
import com.airroute.core.model.RouteMapContextFactory;
import com.airroute.core.model.RouteMapModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anthony
 * @create 2022/1/26
 * @desc
 **/
@Slf4j
public class StationNodeRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * Register bean definitions as necessary based on the given annotation metadata of the importing @Configuration class.
     * @param annotationMetadata
     * @param beanDefinitionRegistry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        try {
            //get xml
            String configFile = (String) annotationMetadata.getAnnotationAttributes(EnableAirRoute.class.getName()).get("value");
            //parse xml and get route map
            ClasspathXmlStationParser classpathXmlStationParser = new ClasspathXmlStationParser(configFile);
            List<RouteMapModel> routeMapModelList = classpathXmlStationParser.parse();
            //register bean definition
            BeanDefinitionBuilder bdb = BeanDefinitionBuilder.rootBeanDefinition(RouteMapContextFactory.class);
            bdb.addConstructorArgValue(new ArrayList<>(routeMapModelList));
            bdb.addConstructorArgReference("springBeanFlightProcessorCreator");
            beanDefinitionRegistry.registerBeanDefinition(RouteMapContextFactory.class.getName(), bdb.getBeanDefinition());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
