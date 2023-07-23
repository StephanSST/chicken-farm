package ch.stephan.chickenfarm;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.stephan.chickenfarm.scale.ExampleSimple;
import ch.stephan.chickenfarm.scale.ScaleService;

@RestController
public class GreetingController {
	private static final String greeting = "Hello, %s!";
	private static final String measure = "Weight of box %s is %s!";
	private final AtomicLong counter = new AtomicLong();

	@Autowired
	private ScaleService scaleService;

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(greeting, name));
	}

	@GetMapping("/measure")
	public Greeting measure(@RequestParam(value = "box", defaultValue = "1") int box) {
		return new Greeting(box, String.format(measure, box, scaleService.measureWeight(box)));
	}

	@GetMapping("/discovery")
	public Greeting discovery() {
		ExampleSimple.discovery();
		return new Greeting(counter.incrementAndGet(), "discovey in log");
	}

}
