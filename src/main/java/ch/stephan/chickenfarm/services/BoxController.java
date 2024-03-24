package ch.stephan.chickenfarm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.stephan.chickenfarm.dto.Box;
import ch.stephan.chickenfarm.dto.BoxState;
import ch.stephan.chickenfarm.dto.Chicken;
import ch.stephan.chickenfarm.registry.BoxService;

@RestController
public class BoxController {

	@Autowired
	private BoxService boxService;

	@GetMapping("/boxes")
	public List<Box> get() {
		return boxService.getBoxes();
	}

	@GetMapping("/boxes/{id}")
	public Box changeWeightOfBox(@PathVariable String id, //
			@RequestParam(value = "weight", required = false) String weight, //
			@RequestParam(value = "state", required = false) String state, //
			@RequestParam(value = "chicken", required = false) String chicken //
	) {
		Box box = boxService.getBox(id);

		if (weight != null) {
			box.setWeight(Integer.valueOf(weight));
		}

		BoxState boxState = BoxState.findByName(state);
		if (boxState != null) {
			box.setBoxState(boxState);
		}

		Chicken matchedChicken = Chicken.findByName(chicken);
		if (matchedChicken != null || "null".equalsIgnoreCase(chicken)) {
			box.setChicken(matchedChicken);
		}

		return box;
	}

}
