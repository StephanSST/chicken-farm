package ch.stephan.chickenfarm.mqtt;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import ch.stephan.chickenfarm.registry.BoxService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@Profile("maven")
public class MockedMqttConfig {

	@MockBean
	private BoxService boxService;

	@MockBean
	private MessageChannel mqttInputChannel;

	@MockBean
	private MqttPahoClientFactory mqttClientFactory;

	@MockBean
	private MessageProducer inbound;

	@MockBean
	private MessageHandler handler;

	@Bean
	MessageChannel mqttInputChannel() {
		return mqttInputChannel;
	}

	@Bean
	MqttPahoClientFactory mqttClientFactory() {
		return mqttClientFactory;
	}

	@Bean
	MessageProducer inbound() {
		return inbound;
	}

	@Bean
	@ServiceActivator(inputChannel = "mqttInputChannel")
	MessageHandler handler() {
		return handler;
	}

}