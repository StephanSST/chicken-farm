package ch.stephan.chickenfarm.timer;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ch.stephan.chickenfarm.dto.Box;
import ch.stephan.chickenfarm.dto.BoxState;
import ch.stephan.chickenfarm.dto.Chicken;
import ch.stephan.chickenfarm.messenger.MessengerService;
import ch.stephan.chickenfarm.registry.BoxService;
import ch.stephan.chickenfarm.registry.ChickenService;
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

	@Autowired
	private ChickenService chickenService;

	@Scheduled(fixedRateString = "${schedulerservice.fixedRate}")
	public void measureWeights() {
		boxService.getBoxes().stream()//
				.forEach(this::measureWeightOfScale);
	}

	private void measureWeightOfScale(Box box) {
		int weight = scaleService.measureWeight(box.getId());

		if (weight > 1000) { // chicken in the box
			Chicken chicken = chickenService.getChicken(weight);
			String message = String.format(":chicken: %s sitzt in der Legebox %s und ist %sg schwer.", chicken.name(),
					box.getDescription(), weight);
			log.info(message);

			if (box.getBoxState() == BoxState.EMPTY) {
				String result = messengerService.sendNotification(message);
				log.info("Message sent: {}", result);
				box.setBoxState(BoxState.CHICKEN_IN);
			}

		} else if (weight > 50) { // egg in the box
			Chicken chicken = chickenService.getChicken(weight);
			String message = String.format(":nest_with_eggs: %s in der Legebox %s hat ein Ei von %sg gelegt.",
					chicken.name(), box.getDescription(), weight);
			log.info(message);

			if (box.getBoxState() == BoxState.CHICKEN_IN) {
				String result = messengerService.sendNotification(message);
				log.info("Message sent: {}", result);
				box.setBoxState(BoxState.EGG_IN);
			}

		} else { // nothing special
			log.info("Box {} ({}) with weight {}g at {}", box.getId(), box.getDescription(), weight,
					dateFormat.format(new Date()));
			box.setBoxState(BoxState.EMPTY);
			scaleService.tare(box.getId());
		}

	}

}