package ch.stephan.chickenfarm.mqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import ch.stephan.chickenfarm.registry.BoxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MqttConfig {

	@Autowired
	private BoxService boxService;

	@Value("${mqtt.user}")
	private String user;

	@Value("${mqtt.password}")
	private String password;

	@Bean
	MessageChannel mqttInputChannel() {
		return new DirectChannel();
	}

	@Bean
	MqttPahoClientFactory clientFactory() {
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		MqttConnectOptions options = new MqttConnectOptions();
		options.setServerURIs(new String[] { "tcp://huehnerstall:1883" });
		options.setUserName(user);
		options.setPassword(password.toCharArray());
		factory.setConnectionOptions(options);
		return factory;
	}

	@Bean
	MessageProducer inbound() {
		MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("RaspberryPi",
				clientFactory(), "/chicken-farm/replies");

		adapter.setCompletionTimeout(5000);
		adapter.setConverter(new DefaultPahoMessageConverter());
		adapter.setQos(1);
		adapter.setOutputChannel(mqttInputChannel());
		return adapter;
	}

	@Bean
	@ServiceActivator(inputChannel = "mqttInputChannel")
	MessageHandler handler() {
		return message -> handleWeight(message);
	}

	protected void handleWeight(Message<?> message) {
		String payload = (String) message.getPayload();
		log.info("Current weights: " + payload);
		MyMessageHandler.handleWeightMessage(payload, boxService);
	}

}