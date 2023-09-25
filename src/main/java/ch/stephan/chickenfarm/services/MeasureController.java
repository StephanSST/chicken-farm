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
	private static final String POINT = ".";
	private static final String COMMA = ", ";
	private static final String EMPTY_STRING = "";
	private static final String MEASURE = "Weight of box %s (%s) is %s";
	private static final String CALIBRATE = "Calibrated box %s, result: %s.";
	private static final String TARE = "Tared box %s, result: %s.";

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
				.map(b -> {
					int weight = scaleService.measureWeight(b.id());
					return String.format(MEASURE, b.id(), b.description(), weight);
				})//
				.collect(Collectors.joining(COMMA, EMPTY_STRING, POINT));
		return new Message(counter.incrementAndGet(), message);
	}

	@GetMapping("/calibrate")
	public Message calibrate(@RequestParam(value = "uid") String uid) {
		return new Message(counter.incrementAndGet(), String.format(CALIBRATE, uid, scaleService.calibrate(uid)));
	}

	@GetMapping("/tare")
	public Message tare(@RequestParam(value = "uid") String uid) {
		return new Message(counter.incrementAndGet(), String.format(TARE, uid, scaleService.tare(uid)));
	}

	@GetMapping("/send")
	public Message send(@RequestParam(value = "text") String text) {
		String result = messengerService.sendNotification(text);
		return new Message(counter.incrementAndGet(), result);
	}

}
