package ch.stephan.chickenfarm.services;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.stephan.chickenfarm.dto.Message;
import ch.stephan.chickenfarm.messenger.MessengerService;
import ch.stephan.chickenfarm.scale.ScaleService;

@RestController
public class MeasureController {
	private static final String greeting = "Hello, %s!";
	private static final String measure = "Weight of box %s is %s.";
	private static final String calibrate = "Calibrated box %s, result: %s.";
	private final AtomicLong counter = new AtomicLong();

	@Autowired
	private ScaleService scaleService;

	@Autowired
	private MessengerService messengerService;

	@GetMapping("/greeting")
	public Message greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Message(counter.incrementAndGet(), String.format(greeting, name));
	}

	@GetMapping("/measure")
	public Message measure(@RequestParam(value = "uid", defaultValue = "ZUw") String uid) {
		return new Message(counter.incrementAndGet(), String.format(measure, uid, scaleService.measureWeight(uid)));
	}

	@GetMapping("/calibrate")
	public Message calibrate(@RequestParam(value = "uid", defaultValue = "ZUw") String uid) {
		return new Message(counter.incrementAndGet(), String.format(calibrate, uid, scaleService.calibrate(uid)));
	}

	@GetMapping("/send")
	public Message send() {
		String result = messengerService.sendNotification("Huhn hat ein Ei gelegt");
		return new Message(counter.incrementAndGet(), result);
	}

}
