package ch.stephan.chickenfarm.scale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.stephan.chickenfarm.mqtt.MessagingService;
import ch.stephan.chickenfarm.registry.BoxService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ScaleService {

	@Autowired
	private BoxService boxService;

	@Autowired
	private MessagingService messagingService;

	public ScaleService() {
		super();
	}

	public int measureWeight(String uid) {
		int weight = boxService.getBox(uid).getWeight();
		log.info("Scale {} has weight {}g.", uid, weight);
		return weight;
	}

	public String calibrate(String uid) {
//		log.info("Scale {} was recalibrated. Before: {}g - after: {}g.", uid, before, after);
		return "successfully calibrated";
	}

	public String tare(String uid) {
		messagingService.publish("tare:" + uid);
		log.info("Scale {} has been tared.", uid);
		return "successfully tared";
	}

}
