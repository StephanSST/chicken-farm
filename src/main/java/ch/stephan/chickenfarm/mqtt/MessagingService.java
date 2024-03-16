package ch.stephan.chickenfarm.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.stephan.chickenfarm.mqtt.MqttConfigConsumer.MyGateway;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MessagingService {

	@Autowired
	private MyGateway myGateway;

	public void publish(String message) {
		try {
			log.info("publishing message '{}'", message);
			myGateway.sendToMqtt("foo");

//			MqttMessage mqttMessage = new MqttMessage();
//			mqttMessage.setPayload("tare:2".getBytes());
////			mqttMessage.setPayload(message.getBytes());
//			mqttMessage.setQos(0);
//			mqttMessage.setRetained(true);
//
//			mqttClient.publish(topic, mqttMessage);
			log.info("message '{}' published", message);
		} catch (Exception ex) {
			log.error("unexpected error publishing the message", ex);
		}

	}

}