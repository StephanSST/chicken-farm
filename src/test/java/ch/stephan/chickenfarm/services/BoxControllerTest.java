package ch.stephan.chickenfarm.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.stephan.chickenfarm.dto.Box;
import ch.stephan.chickenfarm.dto.Chicken;
import ch.stephan.chickenfarm.dto.Measure;

@WebMvcTest(value = BoxController.class)
@ActiveProfiles("maven")
@Disabled("Duplicate mock definition for BoxService, reason unknown")
class BoxControllerTest {

	private static final Box BOX1 = Box.HINTEN;
	private static final Box BOX2 = Box.VORNE;
	private static final Chicken CHICKEN1 = Chicken.HEIDI;
	private static final Chicken CHICKEN2 = Chicken.KLARA;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

//	@Autowired
//	private BoxService boxService;
//
	@BeforeEach
	void setUp() {
//		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGet() throws Exception {
		String mockMvcResult = mockMvc.perform(get("/boxes").contentType(MediaType.APPLICATION_JSON))//
				.andExpect(status().isOk())//
				.andReturn()//
				.getResponse()//
				.getContentAsString();

		List<Measure> boxes = objectMapper.readValue(mockMvcResult, new TypeReference<>() {
		});

		assertNotNull(boxes);
		assertThat(boxes).hasSize(2);
		assertThat(boxes.get(0).boxId()).isEqualTo(BOX1.getId());
		assertThat(boxes.get(0).boxDescription()).isEqualTo(BOX1.getDescription());
		assertThat(boxes.get(0).currentWeight()).isEqualTo(CHICKEN1.getWeight());
		assertThat(boxes.get(0).currentChicken()).isEqualTo(CHICKEN1.name());
		assertThat(boxes.get(1).boxId()).isEqualTo(BOX2.getId());
		assertThat(boxes.get(1).boxDescription()).isEqualTo(BOX2.getDescription());
		assertThat(boxes.get(1).currentWeight()).isEqualTo(CHICKEN2.getWeight());
		assertThat(boxes.get(1).currentChicken()).isEqualTo(CHICKEN2.name());
	}

}
