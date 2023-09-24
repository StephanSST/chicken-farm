package ch.stephan.chickenfarm.registry;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ch.stephan.chickenfarm.dto.Box;
import jakarta.annotation.PostConstruct;

@Service
public class BoxService {

	private static List<Box> sBoxes;

	public BoxService() {
		super();
	}

	public List<Box> getBoxes() {
		return sBoxes;
	}

	@PostConstruct
	public void initBoxes() {
		sBoxes = new ArrayList<>();
		sBoxes.add(new Box("23yp", "hinten"));
		sBoxes.add(new Box("ZUw", "vorne"));
	}

}
