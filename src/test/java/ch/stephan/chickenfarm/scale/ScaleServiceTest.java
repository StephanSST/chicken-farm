package ch.stephan.chickenfarm.scale;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.tinkerforge.IPConnection;

class ScaleServiceTest {
	private static final String UID = UUID.randomUUID().toString();

	@Mock
	private IPConnection mockedIpConnection;

	private ScaleService scaleService;

	@BeforeEach
	void setUp() {
		scaleService = new ScaleService(mockedIpConnection);
	}

	@Test
	void test() {
		// when(mockedIpConnection.).thenReturn(discoveries);

		// int result = scaleService.measureWeight(UID);

		// fail("Not yet implemented");
	}

}
