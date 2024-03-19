package ch.stephan.chickenfarm.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ch.stephan.chickenfarm.mqtt.MqttPublisherService;
import ch.stephan.chickenfarm.registry.BoxService;

@ExtendWith(MockitoExtension.class)
class ScaleServiceTest {

	private static final String BOX_ID = "2";

	private ScaleService scaleService;

	@InjectMocks
	private BoxService boxService;

	@Mock
	private MqttPublisherService mqttPublisherService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		boxService.initBoxes();

		scaleService = new ScaleService(boxService, mqttPublisherService);
	}

	@Test
	void measureWeight() {
		int expectedWeight = 85;

		boxService.getBox(BOX_ID).setWeight(expectedWeight);
		int result = scaleService.measureWeight(BOX_ID);

		assertEquals(expectedWeight, result);
	}

	@Test
	void calibrate() {
		String result = scaleService.calibrate(BOX_ID);

		assertEquals("successfully calibrated", result);
	}

	@Test
	void tare() {
		String result = scaleService.tare(BOX_ID);

		assertEquals("successfully tared", result);
	}

}
