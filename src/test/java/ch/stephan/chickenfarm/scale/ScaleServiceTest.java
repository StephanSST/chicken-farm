package ch.stephan.chickenfarm.scale;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScaleServiceTest {
	private static final String UID = UUID.randomUUID().toString();

	private ScaleService scaleService;

	@BeforeEach
	void setUp() {
		scaleService = new ScaleService();
	}

	@Test
	void test() {
		// when(mockedIpConnection.).thenReturn(discoveries);

		// int result = scaleService.measureWeight(UID);

		// fail("Not yet implemented");
	}

}
