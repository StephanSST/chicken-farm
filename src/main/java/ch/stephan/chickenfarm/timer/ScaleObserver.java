package ch.stephan.chickenfarm.timer;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ch.stephan.chickenfarm.messenger.MessengerService;
import ch.stephan.chickenfarm.registry.BoxService;
import ch.stephan.chickenfarm.scale.ScaleService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ScaleObserver {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	private BoxService boxService;

	@Autowired
	private ScaleService scaleService;

	@Autowired
	private MessengerService messengerService;

	@Scheduled(fixedRateString = "${schedulerservice.fixedRate}")

	public void measureWeights() {
		boxService.getBoxes().stream()//
				.forEach(b -> measureWeightOfScale(b.id()));
	}

	private void measureWeightOfScale(String uid) {
		int weight = scaleService.measureWeight(uid);

		if (weight > 1000) { // chicken in the box
			String message = MessageFormatter
					.format("Ein Huhn sitzt in der Legebox {} und ist {}g schwer.", uid, weight).getMessage();
			log.info(message);
			String result = messengerService.sendNotification(message);
			log.info("Message sent: {}", result);

		} else if (weight > 50) { // egg in the box
			String message = MessageFormatter.format("Huhn in der Legebox {} hat ein Ei von {}g gelegt.", uid, weight)
					.getMessage();
			log.info(message);
			String result = messengerService.sendNotification(message);
			log.info("Message sent: {}", result);

		} else { // nothing special
			log.info("Box {} with weight {}g at {}", uid, weight, dateFormat.format(new Date()));
		}

	}

}