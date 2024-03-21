package ch.stephan.chickenfarm.timer;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ch.stephan.chickenfarm.messenger.MessengerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class MorningGreetings {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

	@Autowired
	private final MessengerService messengerService;

	@Scheduled(cron = "${schedulerservice.morning.cron}")
	public void greetTheDay() {
		String message = String.format(":sunrise_over_mountains: %s: Ein neuer Tag beginnt...",
				dateFormat.format(new Date()));
		String messageId = messengerService.sendNotification(message);
		log.info("Message sent: {}", messageId);
	}

}
