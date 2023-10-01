package ch.stephan.chickenfarm.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.stephan.chickenfarm.dto.Chicken;

class ChickenServiceTest {

	private ChickenService chickenService;

	@BeforeEach
	private void setup() {
		chickenService = new ChickenService();
		chickenService.initBoxes();
	}

	@Test
	void testEqualWeight() {
		Chicken chicken = chickenService.getChicken(ChickenService.HEIDI.weight());
		assertEquals(ChickenService.HEIDI, chicken);

		chicken = chickenService.getChicken(ChickenService.KLARA.weight());
		assertEquals(ChickenService.KLARA, chicken);

		chicken = chickenService.getChicken(ChickenService.LILI.weight());
		assertEquals(ChickenService.LILI, chicken);
	}

	@Test
	void testTenGrammLighter() {
		Chicken chicken = chickenService.getChicken(ChickenService.HEIDI.weight() - 10);
		assertEquals(ChickenService.HEIDI, chicken);

		chicken = chickenService.getChicken(ChickenService.KLARA.weight() - 10);
		assertEquals(ChickenService.KLARA, chicken);

		chicken = chickenService.getChicken(ChickenService.LILI.weight() - 10);
		assertEquals(ChickenService.LILI, chicken);
	}

	@Test
	void testTenGrammHeavier() {
		Chicken chicken = chickenService.getChicken(ChickenService.HEIDI.weight() + 10);
		assertEquals(ChickenService.HEIDI, chicken);

		chicken = chickenService.getChicken(ChickenService.KLARA.weight() + 10);
		assertEquals(ChickenService.KLARA, chicken);

		chicken = chickenService.getChicken(ChickenService.LILI.weight() + 10);
		assertEquals(ChickenService.LILI, chicken);
	}

	@Test
	void testJustBetween() {
		Chicken chicken = chickenService.getChicken(2800);
		assertTrue(
				chicken.name().equals(ChickenService.KLARA.name()) || chicken.name().equals(ChickenService.LILI.name()),
				"Chicken was: " + chicken);
	}

}
