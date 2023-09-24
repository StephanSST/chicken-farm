package ch.stephan.chickenfarm.services;

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.stephan.chickenfarm.dto.Message;
import ch.stephan.chickenfarm.messenger.MessengerService;
import ch.stephan.chickenfarm.registry.BoxService;
import ch.stephan.chickenfarm.scale.ScaleService;

@RestController
public class MeasureController {
	private static final String MEASURE = "Weight of box %s is %s.";
	private static final String CALIBRATE = "Calibrated box %s, result: %s.";

	private final AtomicLong counter = new AtomicLong();

	@Autowired
	private ScaleService scaleService;

	@Autowired
	private MessengerService messengerService;

	@Autowired
	private BoxService boxService;

	@GetMapping("/measure")
	public Message measure() {
		String message = boxService.getBoxes().stream()//
				.map(b -> b.id())//
				.map(id -> scaleService.measureWeight(id))//
				.map(m -> Integer.valueOf(m))//
				.map(f -> String.format(MEASURE, "id", f))//
				.collect(Collectors.joining(", "));
		return new Message(counter.incrementAndGet(), message);
	}

	@GetMapping("/calibrate")
	public Message calibrate(@RequestParam(value = "uid") String uid) {
		return new Message(counter.incrementAndGet(), String.format(CALIBRATE, uid, scaleService.calibrate(uid)));
	}

	@GetMapping("/send")
	public Message send() {
		String result = messengerService.sendNotification("Huhn hat ein Ei gelegt");
		return new Message(counter.incrementAndGet(), result);
	}

}
