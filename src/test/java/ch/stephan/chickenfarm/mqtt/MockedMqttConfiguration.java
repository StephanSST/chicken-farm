package ch.stephan.chickenfarm.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({ "maven", "dev" })
@Configuration
public class MockedMqttConfiguration {

	@MockBean
	private IMqttClient mqttClient;

	@Bean
	IMqttClient mqttClient() throws MqttException {
		return mqttClient;
	}

}