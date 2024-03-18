package ch.stephan.chickenfarm.services;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.stephan.chickenfarm.dto.Measure;
import ch.stephan.chickenfarm.dto.Message;
import ch.stephan.chickenfarm.messenger.MessengerService;
import ch.stephan.chickenfarm.registry.BoxService;
import ch.stephan.chickenfarm.registry.ChickenService;
import ch.stephan.chickenfarm.scale.ScaleService;

@RestController
public class MeasureController {
	private static final String POINT = ".";
	private static final String COMMA = ", ";
	private static final String EMPTY_STRING = "";
	private static final String CALIBRATE = "Calibrated box %s, result: %s.";
	private static final String TARE = "Tared box %s (%s), result: %s";

	private final AtomicLong counter = new AtomicLong();

	@Autowired
	private ScaleService scaleService;

	@Autowired
	private MessengerService messengerService;

	@Autowired
	private BoxService boxService;

	@Autowired
	private ChickenService chickenService;

	@GetMapping("/measure")
	public List<Measure> measure() {
		return boxService.getBoxes().stream()//
				.map(b -> {
					int weight = scaleService.measureWeight(b.getId());
					String chicken = (weight > 500) ? chickenService.guessChicken(weight).name() : null;
					return new Measure(b.getId(), b.getDescription(), weight, chicken);
				})//
				.collect(Collectors.toList());
	}

	@GetMapping("/calibrate")
	public Message calibrate(@RequestParam(value = "uid") String uid) {
		return new Message(counter.incrementAndGet(), String.format(CALIBRATE, uid, scaleService.calibrate(uid)));
	}

	@GetMapping("/tare")
	public Message tare(@RequestParam(value = "uid", required = false) String uid) {
		String message = boxService.getBoxes().stream()//
				.filter(b -> uid == null || b.getId().equals(uid))//
				.map(b -> {
					String result = scaleService.tare(b.getId());
					return String.format(TARE, b.getId(), b.getDescription(), result);
				})//
				.collect(Collectors.joining(COMMA, EMPTY_STRING, POINT));
		return new Message(counter.incrementAndGet(), message);
	}

	@GetMapping("/send")
	public Message send(@RequestParam(value = "text") String text) {
		String result = messengerService.sendNotification(":wink: " + text);
		return new Message(counter.incrementAndGet(), result);
	}

}
