package ch.stephan.chickenfarm.scale;

import org.springframework.stereotype.Service;

@Service
public class ScaleService {

	public int measureWeight(int box) {
		return ExampleSimple.measureWeight();
	}

}
