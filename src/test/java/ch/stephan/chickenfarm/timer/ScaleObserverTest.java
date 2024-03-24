package ch.stephan.chickenfarm.timer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ch.stephan.chickenfarm.dto.Box;
import ch.stephan.chickenfarm.dto.BoxState;
import ch.stephan.chickenfarm.dto.Chicken;
import ch.stephan.chickenfarm.messenger.MessengerService;
import ch.stephan.chickenfarm.registry.BoxService;
import ch.stephan.chickenfarm.registry.ChickenService;
import ch.stephan.chickenfarm.scale.ScaleService;

@ExtendWith(MockitoExtension.class)
class ScaleObserverTest {

	private ScaleObserver scaleObserver;

	@InjectMocks
	private BoxService boxService;

	@Mock
	private ScaleService mockedScaleService;

	@Mock
	private MessengerService mockMessengerService;

	@InjectMocks
	private ChickenService chickenService;

	@BeforeEach
	void setUp() throws Exception {
		Box.HINTEN.setWeight(0);
		Box.VORNE.setWeight(0);
		boxService.initBoxes();
		chickenService.initBoxes();

		scaleObserver = new ScaleObserver(boxService, mockedScaleService, mockMessengerService, chickenService);
	}

	@Test
	void testMeasureWeights_VanillaCase() {
		// Step 1: Two chickens are in the house
		Mockito.when(mockedScaleService.measureWeight(eq(Box.HINTEN.getId())))
				.thenReturn(Chicken.KLARA.getWeight() + 13);
		Mockito.when(mockedScaleService.measureWeight(eq(Box.VORNE.getId())))
				.thenReturn(Chicken.HEIDI.getWeight() + 17);

		scaleObserver.measureWeights();

		Mockito.verify(mockMessengerService, times(2)).sendNotification(anyString());

		// measure again, no new message
		scaleObserver.measureWeights();

		Mockito.verify(mockMessengerService, times(2)).sendNotification(anyString());

		// Step 2: Chickens laid eggs and left the house
		Mockito.when(mockedScaleService.measureWeight(eq(Box.HINTEN.getId()))).thenReturn(67);
		Mockito.when(mockedScaleService.measureWeight(eq(Box.VORNE.getId()))).thenReturn(64);

		scaleObserver.measureWeights();

		Mockito.verify(mockMessengerService, times(4)).sendNotification(anyString());

		// measure again, no new message
		scaleObserver.measureWeights();

		Mockito.verify(mockMessengerService, times(4)).sendNotification(anyString());

		// Step 3: Pamela removed the eggs
		Mockito.when(mockedScaleService.measureWeight(eq(Box.HINTEN.getId()))).thenReturn(2);
		Mockito.when(mockedScaleService.measureWeight(eq(Box.VORNE.getId()))).thenReturn(-3);

		scaleObserver.measureWeights();

		Mockito.verify(mockMessengerService, times(6)).sendNotification(anyString());

	}

	@Test
	void testMeasureWeights_TwoChickensLayEggInSameBox() {
		// Step 1: Klara is in the house
		Mockito.when(mockedScaleService.measureWeight(eq(Box.HINTEN.getId())))
				.thenReturn(Chicken.KLARA.getWeight() - 13);

		scaleObserver.measureWeights();

		assertEquals(Chicken.KLARA, Box.HINTEN.getChicken());
		assertEquals(BoxState.CHICKEN_IN, Box.HINTEN.getBoxState());
		Mockito.verify(mockMessengerService, times(1)).sendNotification(anyString());

		// measure again, no new message
		scaleObserver.measureWeights();

		assertEquals(Chicken.KLARA, Box.HINTEN.getChicken());
		assertEquals(BoxState.CHICKEN_IN, Box.HINTEN.getBoxState());
		Mockito.verify(mockMessengerService, times(1)).sendNotification(anyString());

		// Step 2: Klara laid egg and left the house
		Mockito.when(mockedScaleService.measureWeight(eq(Box.HINTEN.getId()))).thenReturn(67);

		scaleObserver.measureWeights();

		assertNull(Box.HINTEN.getChicken());
		assertEquals(BoxState.EGG_IN, Box.HINTEN.getBoxState());
		Mockito.verify(mockMessengerService, times(2)).sendNotification(anyString());

		// measure again, no new message
		scaleObserver.measureWeights();

		assertNull(Box.HINTEN.getChicken());
		assertEquals(BoxState.EGG_IN, Box.HINTEN.getBoxState());
		Mockito.verify(mockMessengerService, times(2)).sendNotification(anyString());

		// Step 3: Heidi is in the house
		Mockito.when(mockedScaleService.measureWeight(eq(Box.HINTEN.getId())))
				.thenReturn(Chicken.HEIDI.getWeight() + 3);

		scaleObserver.measureWeights();

		assertEquals(Chicken.HEIDI, Box.HINTEN.getChicken());
		assertEquals(BoxState.CHICKEN_IN, Box.HINTEN.getBoxState());
		Mockito.verify(mockMessengerService, times(3)).sendNotification(anyString());

		// measure again, no new message
		scaleObserver.measureWeights();

		assertEquals(Chicken.HEIDI, Box.HINTEN.getChicken());
		assertEquals(BoxState.CHICKEN_IN, Box.HINTEN.getBoxState());
		Mockito.verify(mockMessengerService, times(3)).sendNotification(anyString());

		// Step 4: Heidi laid egg and left the house
		Mockito.when(mockedScaleService.measureWeight(eq(Box.HINTEN.getId()))).thenReturn(139);

		scaleObserver.measureWeights();

		assertNull(Box.HINTEN.getChicken());
		assertEquals(BoxState.EGG_IN, Box.HINTEN.getBoxState());
		Mockito.verify(mockMessengerService, times(4)).sendNotification(anyString());

		// measure again, no new message
		scaleObserver.measureWeights();

		assertNull(Box.HINTEN.getChicken());
		assertEquals(BoxState.EGG_IN, Box.HINTEN.getBoxState());
		Mockito.verify(mockMessengerService, times(4)).sendNotification(anyString());

	}

}
