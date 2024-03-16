package ch.stephan.chickenfarm.mqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class MqttConfigConsumer {

	private static final String MQTT_CLIENT_ID = "RaspPiConsumer";

	@Value("${mqtt.user}")
	private String user;

	@Value("${mqtt.password}")
	private String password;

	@Bean
	MessageChannel mqttOutboundChannel() {
		log.info("mqttOutboundChannel called.");
		return new DirectChannel();
	}

	@Bean
	MqttPahoClientFactory mqttClientFactory() {
		log.info("mqttClientFactory called.");
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		MqttConnectOptions options = new MqttConnectOptions();
		options.setServerURIs(new String[] { "tcp://huehnerstall:1883" });
		options.setUserName(user);
		options.setPassword(password.toCharArray());
		factory.setConnectionOptions(options);
		return factory;
	}

	@Bean
	@ServiceActivator(inputChannel = "mqttOutboundChannel")
	MessageHandler mqttOutbound() {
		log.info("mqttOutbound called.");
		MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(MQTT_CLIENT_ID, mqttClientFactory());
		messageHandler.setAsync(true);
		messageHandler.setDefaultTopic("testTopic");
		return messageHandler;
	}

	@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
	public interface MyGateway {
		void sendToMqtt(String data);
	}
}