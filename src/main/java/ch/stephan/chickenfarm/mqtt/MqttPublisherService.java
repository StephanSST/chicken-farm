package ch.stephan.chickenfarm.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class MqttPublisherService {
	private static final String TOPIC = "/chicken-farm/commands";

	@Autowired
	private IMqttClient mqttClient;

	public void publish(String message) {
		try {
			MqttMessage mqttMessage = new MqttMessage();
			mqttMessage.setPayload(message.getBytes());
			mqttMessage.setQos(0);
			mqttMessage.setRetained(true);

			mqttClient.publish(TOPIC, mqttMessage);

		} catch (Exception ex) {
			log.error("unexpected error publishing the message", ex);
		}

	}

}