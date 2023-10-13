package ch.stephan.chickenfarm.registry;

import ch.stephan.chickenfarm.dto.Box;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

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

}
