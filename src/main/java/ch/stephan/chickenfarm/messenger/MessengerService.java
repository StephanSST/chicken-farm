package ch.stephan.chickenfarm.messenger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MessengerService {

	@Value("${messengerservice.channel}")
	private String channel;

	@Value("${messengerservice.token}")
	private String token;

	@Value("${messengerservice.enabled}")
	private boolean enabled;

	public String sendNotification(String message) {
		if (!enabled) {
			return "Messaging was disabled, no message sent.";
		}

		try {
			Slack slack = Slack.getInstance();
			// Initialize an API Methods client with the given token
			MethodsClient methods = slack.methods(token);

			// Build a request object
			ChatPostMessageRequest request = ChatPostMessageRequest.builder().channel(channel)//
					.text(message).build();

			// Get a response as a Java object
			ChatPostMessageResponse response = methods.chatPostMessage(request);
			if (response.isOk()) {
				String messageId = response.getHttpResponseHeaders().get("x-slack-unique-id").toString();
				log.info("Message with id {} successfully sent.", messageId);
				return messageId;
			} else {
				String errorCode = response.getError();
				return "Error sending message with error " + errorCode;
			}
		} catch (Exception ex) {
			log.error("Unexpected exception calling the Slack API", ex);
			return "Error sending message with error " + ex.getMessage();
		}
	}

}
