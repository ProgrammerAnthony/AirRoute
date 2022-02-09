# AirRoute
AirRoute is a process engine, which helps us to manage,process and rollback 

AirRoute 是一个流程引擎，可通过配置文件动态管理，运行和回滚流程

![](icon.jpg)

# quick start

import module
模块引入
```
        <dependency>
            <groupId>com.airroute</groupId>
            <artifactId>AirRoute-Core</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
```

define a common process node, which named as ‘NormalFlight‘(extends from NormalFlightProcessor) ,
data will flow into 'onArriveProcess' method

定义普通节点，继承自NormalFlightProcessor，节点数据进行到这里的时候会调用onArriveProcess方法
```
@Slf4j
@Component
public class NormalFlightProcessorDemo extends NormalFlightProcessor {
    public NormalFlightProcessorDemo(){}
    @Override
    protected void onArriveProcess(FlightInfoContext flightInfoContext) {
        System.out.println("NormalFlightProcessorDemo id: " + flightInfoContext.getPackage("id"));
    }
}

```


define a rollback process node, which named as ‘ReturnFlight‘(extends from ReturnFlightProcessor) ,
data will flow into 'onArriveProcess' method ,and when exception throws('caughtTerroristAttack' method called), data will flow back into
‘onReturnArriveProcess’
定义回滚节点，继承自ReturnFlightProcessor，节点数据进行到这里的时候会调用onArriveProcess方法，节点回滚的时候会调用onReturnArriveProcess方法
异常捕获在caughtTerroristAttack方法里
```
@Slf4j
@Component
public class ReturnFlightProcessorDemo extends ReturnFlightProcessor {
    public ReturnFlightProcessorDemo(){}
    @Override
    protected void onArriveProcess(FlightInfoContext flightInfoContext) {
        System.out.println("ReturnFlightProcessorDemo id: " + flightInfoContext.getPackage("id"));
        System.out.println("ReturnFlightProcessorDemo extra: " +  flightInfoContext.getPackage("extra"));
        int i = 1 / 0;
    }

    @Override
    public void onReturnArriveProcess(FlightInfoContext flightInfoContext) {
        System.out.println("rollback ReturnFlightProcessorDemo " + flightInfoContext.getPackage("id"));
    }
    
    @Override
    public void caughtTerroristAttack(FlightInfoContext flightInfoContext, Throwable throwable) {
        super.caughtTerroristAttack(flightInfoContext, throwable);
    }
}

```

route map define in xml resource file to mark flow and every station node
定义整个流程节点执行顺序（飞行地图）
```
<?xml version="1.0" encoding="UTF-8"?>
<air-route-context xmlns="http://www.w3school.com.cn"
                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="http://www.w3school.com.cn air-route.xsd">
    <route-map name="process1">
        <station-node name="node1" class="com.airroute.demo.annotation.NormalFlightProcessorDemo" next="node2" begin="true"/>
        <station-node name="node2" class="com.airroute.demo.annotation.NormalFlightProcessorDemo2" next="node3"/>
        <station-node name="node3" class="com.airroute.demo.annotation.ReturnFlightProcessorDemo" next="node4"/>
        <station-node name="node4" class="com.airroute.demo.annotation.NormalFlightProcessorDemo4"/>
        <station-node name="node5" class="com.airroute.demo.annotation.NormalFlightProcessorDemo5" invoke-method="SYNC"/>
    </route-map>
</air-route-context>
```

finally ,we call take off ,and we can load any object package to pass to next node.

调用getFlightInfoContext获取对应的流程节点执行顺序，通过loadPackage可以添加数据给后续节点，调用takeoff开始执行对应流程节点
```
    @GetMapping("/test1")
    public String test1() {
        FlightInfoContext process1 = routeMapContextFactory.getFlightInfoContext("process1");
        process1.loadPackage("id", "process1");
        process1.takeOff();
        return "true";
    }

```