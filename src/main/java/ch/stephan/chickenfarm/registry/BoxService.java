package ch.stephan.chickenfarm.registry;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ch.stephan.chickenfarm.dto.Box;
import jakarta.annotation.PostConstruct;

@Service
public class BoxService {

	private static List<Box> sBoxes = null;

	public List<Box> getBoxes() {
		return sBoxes;
	}

	@PostConstruct
	public void initBoxes() {
		sBoxes = new ArrayList<>();
		sBoxes.add(Box.HINTEN);
		sBoxes.add(Box.VORNE);
	}

	public Box getBox(String id) {
		return sBoxes.stream()//
				.filter(b -> b.getId().equals(id))//
				.findFirst()//
				.orElseThrow(() -> new IllegalArgumentException(String.format("no box with id %s available", id)));
	}
}
