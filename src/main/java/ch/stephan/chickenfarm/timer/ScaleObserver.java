package ch.stephan.chickenfarm.timer;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ch.stephan.chickenfarm.scale.ScaleService;

@Component
public class ScaleObserver {

	private static final Logger log = LoggerFactory.getLogger(ScaleObserver.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	private ScaleService scaleService;

	@Scheduled(fixedRate = 5000)
	public void measureWeights() {
		int weight = scaleService.measureWeight(1);
		log.info("Weight {}g at {}", weight, dateFormat.format(new Date()));
	}
	
}
