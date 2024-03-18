package ch.stephan.chickenfarm.timer;

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
		boxService.initBoxes();
		chickenService.initBoxes();

		scaleObserver = new ScaleObserver(boxService, mockedScaleService, mockMessengerService, chickenService);
	}

	@Test
	void testMeasureWeights() {
		// Step 1: Two chickens are in the house
		Box vorne = Box.VORNE;
		Box hinten = Box.HINTEN;
		Chicken heidi = Chicken.HEIDI;
		Chicken klara = Chicken.KLARA;

		Mockito.when(mockedScaleService.measureWeight(eq(hinten.getId()))).thenReturn(klara.getWeight() + 13);
		Mockito.when(mockedScaleService.measureWeight(eq(vorne.getId()))).thenReturn(heidi.getWeight() + 17);

		scaleObserver.measureWeights();

		Mockito.verify(mockMessengerService, times(2)).sendNotification(anyString());

		// measure again, no new message
		scaleObserver.measureWeights();

		Mockito.verify(mockMessengerService, times(2)).sendNotification(anyString());

		// Step 2: Chickens laid eggs and left the house
		Mockito.when(mockedScaleService.measureWeight(eq(hinten.getId()))).thenReturn(67);
		Mockito.when(mockedScaleService.measureWeight(eq(vorne.getId()))).thenReturn(64);

		scaleObserver.measureWeights();

		Mockito.verify(mockMessengerService, times(4)).sendNotification(anyString());

		// measure again, no new message
		scaleObserver.measureWeights();

		Mockito.verify(mockMessengerService, times(4)).sendNotification(anyString());

		// Step 3: Pamela removed the eggs
		Mockito.when(mockedScaleService.measureWeight(eq(hinten.getId()))).thenReturn(2);
		Mockito.when(mockedScaleService.measureWeight(eq(vorne.getId()))).thenReturn(-3);

		scaleObserver.measureWeights();

		Mockito.verify(mockMessengerService, times(6)).sendNotification(anyString());

	}

}
