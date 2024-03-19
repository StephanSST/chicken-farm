package ch.stephan.chickenfarm.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Profile("default")
@Configuration
public class MqttConfiguration {

	private static final String MQTT_CLIENT_ID = "RasPiProducer";

	@Value("${mqtt.user}")
	private String user;

	@Value("${mqtt.password}")
	private String password;

	@Bean
	IMqttClient mqttClient() throws MqttException {
		try {
			MqttConnectOptions options = new MqttConnectOptions();
			options.setUserName(user);
			options.setPassword(password.toCharArray());
			options.setAutomaticReconnect(true);
			options.setCleanSession(true);
			options.setConnectionTimeout(10);

			IMqttClient mqttClient = new MqttClient("tcp://huehnerstall:1883", MQTT_CLIENT_ID);
			mqttClient.connect(options);
			log.info("successfully connected to MQTT server");
			return mqttClient;
		} catch (MqttException ex) {
			log.error("cannot connect to mqtt server", ex);
			throw ex;
		}
	}

}