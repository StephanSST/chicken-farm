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
		Chicken chicken = chickenService.guessChicken(Chicken.HEIDI.getWeight());
		assertEquals(Chicken.HEIDI, chicken);

		chicken = chickenService.guessChicken(Chicken.KLARA.getWeight());
		assertEquals(Chicken.KLARA, chicken);

		chicken = chickenService.guessChicken(Chicken.LILI.getWeight());
		assertEquals(Chicken.LILI, chicken);

		chicken = chickenService.guessChicken(Chicken.LULU.getWeight());
		assertEquals(Chicken.LULU, chicken);
	}

	@Test
	void testTenGrammLighter() {
		Chicken chicken = chickenService.guessChicken(Chicken.HEIDI.getWeight() - 10);
		assertEquals(Chicken.HEIDI, chicken);

		chicken = chickenService.guessChicken(Chicken.KLARA.getWeight() - 10);
		assertEquals(Chicken.KLARA, chicken);

		chicken = chickenService.guessChicken(Chicken.LULU.getWeight() - 10);
		assertEquals(Chicken.LULU, chicken);
	}

	@Test
	void testTenGrammHeavier() {
		Chicken chicken = chickenService.guessChicken(Chicken.HEIDI.getWeight() + 10);
		assertEquals(Chicken.HEIDI, chicken);
		chicken = chickenService.guessChicken(Chicken.LILI.getWeight() + 10);
		assertEquals(Chicken.LILI, chicken);

		chicken = chickenService.guessChicken(Chicken.LULU.getWeight() + 10);
		assertEquals(Chicken.LULU, chicken);

	}

}
