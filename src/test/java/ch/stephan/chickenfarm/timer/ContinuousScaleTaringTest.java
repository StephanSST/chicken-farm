package ch.stephan.chickenfarm.timer;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ch.stephan.chickenfarm.registry.BoxService;
import ch.stephan.chickenfarm.scale.ScaleService;

@ExtendWith(MockitoExtension.class)
class ContinuousScaleTaringTest {

	private ContinuousScaleTaring continuousScaleTaring;

	@InjectMocks
	private BoxService boxService;

	@Mock
	private ScaleService mockedScaleService;

	@BeforeEach
	void setUp() throws Exception {
		boxService.initBoxes();
		continuousScaleTaring = new ContinuousScaleTaring(boxService, mockedScaleService);
	}

	@Test
	void test_WhenWeight0_tareIsCalled() {
		setAllWeightsTo(0);
		callAndCheckTimesCalled(2);
	}

	@Test
	void test_WhenWeightMinus165_tareIsCalled() {
		setAllWeightsTo(-165);
		callAndCheckTimesCalled(2);
	}

	@Test
	void test_WhenWeight15_tareIsCalled() {
		setAllWeightsTo(15);
		callAndCheckTimesCalled(2);
	}

	@Test
	void test_WhenWeight65_tareIsCalled() {
		setAllWeightsTo(65);
		callAndCheckTimesCalled(0);
	}

	private void setAllWeightsTo(int weight) {
		boxService.getBoxes().stream()//
				.forEach(b -> b.setWeight(weight));
	}

	private void callAndCheckTimesCalled(int times) {
		continuousScaleTaring.tareAllBoxes();
		Mockito.verify(mockedScaleService, times(times)).tare(anyString());
	}

}
