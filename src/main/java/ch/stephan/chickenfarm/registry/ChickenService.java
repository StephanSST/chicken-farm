package ch.stephan.chickenfarm.registry;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import ch.stephan.chickenfarm.dto.Chicken;
import jakarta.annotation.PostConstruct;

@Service
public class ChickenService {

	private static List<Chicken> sChickens = null;

	public List<Chicken> getAllChickens() {
		return sChickens;
	}

	public Chicken guessChicken(int weight) {
		return sChickens.stream()//
				.reduce(Chicken.HEIDI, (chicken, another) -> betterMatching(chicken, another, weight));
	}

	private Chicken betterMatching(Chicken chicken, Chicken another, int weight) {
		if (Math.abs(weight - chicken.getWeight()) <= Math.abs(weight - another.getWeight())) {
			return chicken;
		} else {
			return another;
		}
	}

	@PostConstruct
	public void initBoxes() {
		sChickens = Arrays.asList(Chicken.values());
	}

}
