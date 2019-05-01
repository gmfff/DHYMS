package org.sang.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.sang.mqtt.MqttGateway;
/**
 * @Author: gmfff
 * @Date: 2019/4/29 15:02
 * @Version 1.0
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private MqttGateway mqttGateway;
    @RequestMapping("/sendMqtt")
    public String sendMqtt(String  sendData){
        System.out.println("来了老弟"+sendData);
        mqttGateway.sendToMqtt(sendData,"hello");
        return "OK";
    }


}
