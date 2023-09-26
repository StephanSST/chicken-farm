package ch.stephan.chickenfarm.registry;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ch.stephan.chickenfarm.dto.Box;
import ch.stephan.chickenfarm.dto.BoxState;
import jakarta.annotation.PostConstruct;

@Service
public class BoxService {

	private static List<Box> sBoxes = null;

	public BoxService() {
		super();
	}

	public List<Box> getBoxes() {
		return sBoxes;
	}

	@PostConstruct
	public void initBoxes() {
		sBoxes = new ArrayList<>();
		sBoxes.add(new Box("23yp", "hinten", BoxState.EMPTY));
		sBoxes.add(new Box("ZUw", "vorne", BoxState.EMPTY));
	}

}
