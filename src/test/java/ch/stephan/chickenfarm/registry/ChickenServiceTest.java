package ch.stephan.chickenfarm.registry;

import ch.stephan.chickenfarm.dto.Chicken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChickenServiceTest {

	private ChickenService chickenService;

	@BeforeEach
	private void setup() {
		chickenService = new ChickenService();
		chickenService.initBoxes();
	}

	@Test
	void testEqualWeight() {
		Chicken chicken = chickenService.getChicken(Chicken.HEIDI.weight());
		assertEquals(Chicken.HEIDI, chicken);

		chicken = chickenService.getChicken(Chicken.KLARA.weight());
		assertEquals(Chicken.KLARA, chicken);

		chicken = chickenService.getChicken(Chicken.LILI.weight());
		assertEquals(Chicken.LILI, chicken);
	}

	@Test
	void testTenGrammLighter() {
		Chicken chicken = chickenService.getChicken(Chicken.HEIDI.weight() - 10);
		assertEquals(Chicken.HEIDI, chicken);

		chicken = chickenService.getChicken(Chicken.KLARA.weight() - 10);
		assertEquals(Chicken.KLARA, chicken);

		chicken = chickenService.getChicken(Chicken.LILI.weight() - 10);
		assertEquals(Chicken.LILI, chicken);
	}

	@Test
	void testTenGrammHeavier() {
		Chicken chicken = chickenService.getChicken(Chicken.HEIDI.weight() + 10);
		assertEquals(Chicken.HEIDI, chicken);

		chicken = chickenService.getChicken(Chicken.KLARA.weight() + 10);
		assertEquals(Chicken.KLARA, chicken);

		chicken = chickenService.getChicken(Chicken.LILI.weight() + 10);
		assertEquals(Chicken.LILI, chicken);
	}

	@Test
	void testJustBetween() {
		Chicken chicken = chickenService.getChicken(2800);
		assertTrue(
				chicken.name().equals(Chicken.KLARA.name()) || chicken.name().equals(Chicken.LILI.name()),
				"Chicken was: " + chicken);
	}

}
