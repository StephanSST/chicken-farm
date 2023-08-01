package ch.stephan.chickenfarm.timer;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ch.stephan.chickenfarm.messenger.MessengerService;
import ch.stephan.chickenfarm.scale.ScaleService;

@Component
public class ScaleObserver {

	private static final Logger log = LoggerFactory.getLogger(ScaleObserver.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	private ScaleService scaleService;

	@Autowired
	private MessengerService whatsappService;

	@Scheduled(fixedRate = 10000)
	public void measureWeights() {
		String uid = "23yp";
		int weight = scaleService.measureWeight(uid);

		if (weight > 1000) { // chicken in the box
			String message = MessageFormatter
					.format("Ein Huhn sitzt in der Legebox {} und ist {}g schwer.", uid, weight).getMessage();
			log.info(message);
			whatsappService.sendNotification(message);

		} else if (weight > 50) { // egg in the box
			String message = MessageFormatter.format("Huhn in der Legebox {} hat ein Ei von {}g gelegt.", uid, weight)
					.getMessage();
			log.info(message);
			whatsappService.sendNotification(message);

		} else { // nothing special
			log.info("Box {} with weight {}g at {}", weight, dateFormat.format(new Date()));
		}

	}

}
