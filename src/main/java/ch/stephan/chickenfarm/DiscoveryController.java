package ch.stephan.chickenfarm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.stephan.chickenfarm.dto.Discovery;
import ch.stephan.chickenfarm.scale.ScaleService;

@RestController
public class DiscoveryController {

	@Autowired
	private ScaleService scaleService;

	@GetMapping("/discovery")
	public List<Discovery> discovery() {
		return scaleService.discovery();
	}

}
