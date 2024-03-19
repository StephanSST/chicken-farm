package ch.stephan.chickenfarm.mqtt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import ch.stephan.chickenfarm.registry.BoxService;

@ExtendWith(MockitoExtension.class)
class ChickenFarmMessageHandlerTest {

	@InjectMocks
	private BoxService boxService;

	@BeforeEach
	void setUp() throws Exception {
		boxService.initBoxes();
	}

	@Test
	void handleWeightMessage() {
		String message = "s1:135.45;s2:-153.92";
		ChickenFarmMessageHandler.handleWeightMessage(message, boxService);
		assertEquals(135, boxService.getBox("1").getWeight());
		assertEquals(-153, boxService.getBox("2").getWeight());
	}

}
