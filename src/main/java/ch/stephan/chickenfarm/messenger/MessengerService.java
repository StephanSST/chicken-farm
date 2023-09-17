package ch.stephan.chickenfarm.messenger;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class MessengerService {
	private static final Logger log = LoggerFactory.getLogger(MessengerService.class);

	private static final String URL = "https://msgapi.threema.ch/send_simple?to={to}&text={text}&from={from}&secret={secret}";

	private final RestTemplate restTemplate;

	@Value("${messengerservice.from}")
	private String from;

	@Value("${messengerservice.to}")
	private String to;

	@Value("${messengerservice.secret}")
	private String secret;

	@Value("${messengerservice.enabled}")
	private boolean enabled;

	public MessengerService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public String sendNotification(String message) {
		if (!enabled) {
			return "Messaging was disabled, not message sent.";
		}

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
			HttpEntity<?> httpEntity = new HttpEntity<>(headers);

			Map<String, String> params = new HashMap<>();
			params.put("to", to);
			params.put("text", message);
			params.put("from", from);
			params.put("secret", secret);

			String messageId = restTemplate.postForObject(URL, httpEntity, String.class, params);

			log.info("Message with id {} successfully sent.", messageId);
			return messageId;

		} catch (HttpStatusCodeException ex) {
			log.error("getStatusCode: " + ex.getStatusCode());
			log.error("getResponseHeaders: " + ex.getResponseHeaders());
			log.error("getResponseBodyAsString: " + ex.getResponseBodyAsString());
			log.error("getMessage: " + ex.getMessage());
			log.error("Error sending message with http code {}, see logs for more info.", ex.getStatusCode());
			return "Error sending message with http code " + ex.getStatusCode();
		}

	}

}
