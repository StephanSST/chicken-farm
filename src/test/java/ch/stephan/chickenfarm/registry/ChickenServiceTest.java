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
		Chicken chicken = chickenService.guessChicken(Chicken.HEIDI.weight());
		assertEquals(Chicken.HEIDI, chicken);

		chicken = chickenService.guessChicken(Chicken.KLARA.weight());
		assertEquals(Chicken.KLARA, chicken);

		chicken = chickenService.guessChicken(Chicken.LILI.weight());
		assertEquals(Chicken.LILI, chicken);

		chicken = chickenService.guessChicken(Chicken.LULU.weight());
		assertEquals(Chicken.LULU, chicken);
	}

	@Test
	void testTenGrammLighter() {
		Chicken chicken = chickenService.guessChicken(Chicken.HEIDI.weight() - 10);
		assertEquals(Chicken.HEIDI, chicken);

		chicken = chickenService.guessChicken(Chicken.KLARA.weight() - 10);
		assertEquals(Chicken.KLARA, chicken);

		chicken = chickenService.guessChicken(Chicken.LULU.weight() - 10);
		assertEquals(Chicken.LULU, chicken);
	}

	@Test
	void testTenGrammHeavier() {
		Chicken chicken = chickenService.guessChicken(Chicken.HEIDI.weight() + 10);
		assertEquals(Chicken.HEIDI, chicken);
		chicken = chickenService.guessChicken(Chicken.LILI.weight() + 10);
		assertEquals(Chicken.LILI, chicken);

		chicken = chickenService.guessChicken(Chicken.LULU.weight() + 10);
		assertEquals(Chicken.LULU, chicken);

	}

}
