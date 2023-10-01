package ch.stephan.chickenfarm.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.stephan.chickenfarm.dto.Chicken;
import ch.stephan.chickenfarm.dto.Measure;
import ch.stephan.chickenfarm.dto.Message;
import ch.stephan.chickenfarm.messenger.MessengerService;
import ch.stephan.chickenfarm.registry.ChickenService;
import ch.stephan.chickenfarm.scale.ScaleService;

@WebMvcTest(value = MeasureController.class)
class MeasureControllerTest {

	private static final String MESSAGE_TEXT = "Huhn hat ein Ei gelegt";
	private static final String CALIBRATION_RESULT = "successfully calibrated";
	private static final String UID1 = "23yp";
	private static final String UID2 = "ZUw";
	private static final Chicken CHICKEN1 = ChickenService.HEIDI;
	private static final Chicken CHICKEN2 = ChickenService.KLARA;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ScaleService scaleService;

	@MockBean
	private MessengerService messengerService;

	@Test
    void testMeasure() throws Exception {
        when(scaleService.measureWeight(eq(UID1))).thenReturn(CHICKEN1.weight());
        when(scaleService.measureWeight(eq(UID2))).thenReturn(CHICKEN2.weight());

        String mockMvcResult = mockMvc.perform(get("/measure").contentType(MediaType.APPLICATION_JSON))//
                .andExpect(status().isOk())//
                .andReturn()//
                .getResponse()//
                .getContentAsString();

        List<Measure> measures = objectMapper.readValue(mockMvcResult, new TypeReference<>() {});

        assertNotNull(measures);
		assertThat(measures).hasSize(2);
		assertThat(measures.get(0).boxId()).isEqualTo(UID1);
		assertThat(measures.get(0).boxDescription()).isEqualTo("hinten");
		assertThat(measures.get(0).currentWeight()).isEqualTo(CHICKEN1.weight());
		assertThat(measures.get(0).currentChicken()).isEqualTo(CHICKEN1.name());
		assertThat(measures.get(1).boxId()).isEqualTo(UID2);
		assertThat(measures.get(1).boxDescription()).isEqualTo("vorne");
		assertThat(measures.get(1).currentWeight()).isEqualTo(CHICKEN2.weight());
		assertThat(measures.get(1).currentChicken()).isEqualTo(CHICKEN2.name());
    }

	@Test
    void testSend() throws Exception {
        when(messengerService.sendNotification(anyString())).thenAnswer(i -> i.getArguments()[0]);
        
        String mockMvcResult = mockMvc.perform(get("/send").queryParam("text", MESSAGE_TEXT).contentType(MediaType.APPLICATION_JSON))//
                .andExpect(status().isOk())//
                .andReturn()//
                .getResponse()//
                .getContentAsString();
        
        Message message = objectMapper.readValue(mockMvcResult, new TypeReference<>() {});
        
        assertNotNull(message);
        assertThat(message.content()).isEqualTo(":wink: " + MESSAGE_TEXT);
    }

	@Test
    void testCalibrate() throws Exception {
        when(scaleService.calibrate(eq(UID1))).thenReturn(CALIBRATION_RESULT);
        
        String mockMvcResult = mockMvc.perform(get("/calibrate").queryParam("uid", UID1).contentType(MediaType.APPLICATION_JSON))//
                .andExpect(status().isOk())//
                .andReturn()//
                .getResponse()//
                .getContentAsString();
        
        Message message = objectMapper.readValue(mockMvcResult, new TypeReference<>() {});
        
        assertNotNull(message);
        assertThat(message.content()).isEqualTo(String.format("Calibrated box %s, result: %s.", UID1, CALIBRATION_RESULT));
    }

}
