package ch.stephan.chickenfarm.timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ch.stephan.chickenfarm.dto.Box;
import ch.stephan.chickenfarm.registry.BoxService;
import ch.stephan.chickenfarm.scale.ScaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
public class ContinuousScaleTaring {

	@Autowired
	private final BoxService boxService;

	@Autowired
	private final ScaleService scaleService;

	@Scheduled(cron = "${schedulerservice.tare.cron}")
	public void tareAllBoxes() {
		boxService.getBoxes().stream()//
				.map(Box::getId) //
				.forEach(this::tareIfNeeded);
	}

	private void tareIfNeeded(String id) {
		Box box = boxService.getBox(id);
		if (Math.abs(box.getWeight()) > 5) {
			scaleService.tare(id);
		} else {
			log.info("No need to tare Scale {}, weight was {}.", id, box.getWeight());
		}
	}
}
