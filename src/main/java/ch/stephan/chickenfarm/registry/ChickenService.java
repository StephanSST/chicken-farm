package ch.stephan.chickenfarm.registry;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ch.stephan.chickenfarm.dto.Chicken;
import jakarta.annotation.PostConstruct;

@Service
public class ChickenService {
	public static final Chicken HEIDI = new Chicken("Heidi", 2000);
	public static final Chicken KLARA = new Chicken("Klara", 2850);
	public static final Chicken LILI = new Chicken("Lili", 2750);

	private static List<Chicken> sChickens = null;

	public List<Chicken> getAllChickens() {
		return sChickens;
	}

	public Chicken getChicken(int weight) {
		return sChickens.stream()//
				.reduce(HEIDI, (chicken, another) -> betterMatching(chicken, another, weight));
	}

	private Chicken betterMatching(Chicken chicken, Chicken another, int weight) {
		if (Math.abs(weight - chicken.weight()) <= Math.abs(weight - another.weight())) {
			return chicken;
		} else {
			return another;
		}
	}

	@PostConstruct
	public void initBoxes() {
		sChickens = new ArrayList<>();
		sChickens.add(HEIDI);
		sChickens.add(KLARA);
		sChickens.add(LILI);
	}

}
