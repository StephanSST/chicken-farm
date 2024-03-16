package ch.stephan.chickenfarm.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
		Chicken chicken = chickenService.getChicken(Chicken.HEIDI.weight());
		assertEquals(Chicken.HEIDI, chicken);

		chicken = chickenService.getChicken(Chicken.KLARA.weight());
		assertEquals(Chicken.KLARA, chicken);

		chicken = chickenService.getChicken(Chicken.LILI.weight());
		assertEquals(Chicken.LILI, chicken);

		chicken = chickenService.getChicken(Chicken.LULU.weight());
		assertEquals(Chicken.LULU, chicken);
	}

	@Test
	void testTenGrammLighter() {
		Chicken chicken = chickenService.getChicken(Chicken.HEIDI.weight() - 10);
		assertEquals(Chicken.HEIDI, chicken);

		chicken = chickenService.getChicken(Chicken.KLARA.weight() - 10);
		assertEquals(Chicken.KLARA, chicken);

		chicken = chickenService.getChicken(Chicken.LULU.weight() - 10);
		assertEquals(Chicken.LULU, chicken);
	}

	@Test
	void testTenGrammHeavier() {
		Chicken chicken = chickenService.getChicken(Chicken.HEIDI.weight() + 10);
		assertEquals(Chicken.HEIDI, chicken);
		chicken = chickenService.getChicken(Chicken.LILI.weight() + 10);
		assertEquals(Chicken.LILI, chicken);

		chicken = chickenService.getChicken(Chicken.LULU.weight() + 10);
		assertEquals(Chicken.LULU, chicken);

	}

}
