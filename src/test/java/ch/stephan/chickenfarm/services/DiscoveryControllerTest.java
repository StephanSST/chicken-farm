package ch.stephan.chickenfarm.services;

import java.util.UUID;

import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.stephan.chickenfarm.scale.ScaleService;

@WebMvcTest(value = DiscoveryController.class)
class DiscoveryControllerTest {
	private static final String DEVICE_IDENTIFIER = "ZH8J";
	private static final String UID = UUID.randomUUID().toString();
	private static final String PARENT_UID = UUID.randomUUID().toString();
	private static final String POSITION = "1";
	private static final String ENUMERATION_TYPE = "55";
	private static final String HARDWARE_VERSION = "1.0.7";
	private static final String FIRMWARE_VERSION = "2.1.4";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ScaleService scaleService;

//	@Test
	@Disabled("always leads to strange threading exceptions")
	void testDiscovery() throws Exception {
//		List<Discovery> discoveries = List.of(new Discovery(DEVICE_IDENTIFIER, UID, PARENT_UID, POSITION,
//				ENUMERATION_TYPE, HARDWARE_VERSION, FIRMWARE_VERSION));
//		when(scaleService.discovery()).thenReturn(discoveries);
//
//		String mockMvcResult = mockMvc.perform(get("/discovery").contentType(MediaType.APPLICATION_JSON))//
//				.andExpect(status().isOk())//
//				.andReturn()//
//				.getResponse()//
//				.getContentAsString();
//
//		List<Discovery> components = objectMapper.readValue(mockMvcResult, new TypeReference<>() {
//		});
//
//		assertThat(components).hasSize(1);
//		assertThat(components.get(0).deviceIdentifier()).isEqualTo(DEVICE_IDENTIFIER);
//		assertThat(components.get(0).uid()).isEqualTo(UID);
//		assertThat(components.get(0).parentUid()).isEqualTo(PARENT_UID);
//		assertThat(components.get(0).position()).isEqualTo(POSITION);
//		assertThat(components.get(0).enumerationType()).isEqualTo(ENUMERATION_TYPE);
//		assertThat(components.get(0).hardwareVersion()).isEqualTo(HARDWARE_VERSION);
//		assertThat(components.get(0).firmwareVersion()).isEqualTo(FIRMWARE_VERSION);
	}

}
