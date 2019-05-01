package org.sang.mqtt;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * @Author: gmfff
 * @Date: 2019/4/29 15:01
 * @Version 1.0
 */

@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttGateway{

    void sendToMqtt(String data,@Header(MqttHeaders.TOPIC) String topic);

}
