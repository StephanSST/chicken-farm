package ch.stephan.chickenfarm.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.stephan.chickenfarm.dto.Box;
import ch.stephan.chickenfarm.dto.Chicken;
import ch.stephan.chickenfarm.dto.Measure;
import ch.stephan.chickenfarm.dto.Message;
import ch.stephan.chickenfarm.messenger.MessengerService;
import ch.stephan.chickenfarm.scale.ScaleService;

@WebMvcTest(value = MeasureController.class)
@Disabled("fix mqtt, mock it")
class MeasureControllerTest {

	private static final String MESSAGE_TEXT = "Huhn hat ein Ei gelegt";
	private static final String CALIBRATION_RESULT = "successfully calibrated";
	private static final String TARE_RESULT = "successfully tared";
	private static final Box BOX1 = Box.HINTEN;
	private static final Box BOX2 = Box.VORNE;
	private static final Chicken CHICKEN1 = Chicken.HEIDI;
	private static final Chicken CHICKEN2 = Chicken.KLARA;

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
        when(scaleService.measureWeight(eq(BOX1.getId()))).thenReturn(CHICKEN1.weight());
        when(scaleService.measureWeight(eq(BOX2.getId()))).thenReturn(CHICKEN2.weight());

        String mockMvcResult = mockMvc.perform(get("/measure").contentType(MediaType.APPLICATION_JSON))//
                .andExpect(status().isOk())//
                .andReturn()//
                .getResponse()//
                .getContentAsString();

        List<Measure> measures = objectMapper.readValue(mockMvcResult, new TypeReference<>() {});

        assertNotNull(measures);
		assertThat(measures).hasSize(2);
		assertThat(measures.get(0).boxId()).isEqualTo(BOX1.getId());
		assertThat(measures.get(0).boxDescription()).isEqualTo(BOX1.getDescription());
		assertThat(measures.get(0).currentWeight()).isEqualTo(CHICKEN1.weight());
		assertThat(measures.get(0).currentChicken()).isEqualTo(CHICKEN1.name());
		assertThat(measures.get(1).boxId()).isEqualTo(BOX2.getId());
		assertThat(measures.get(1).boxDescription()).isEqualTo(BOX2.getDescription());
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
        when(scaleService.calibrate(eq(BOX1.getId()))).thenReturn(CALIBRATION_RESULT);
        
        String mockMvcResult = mockMvc.perform(get("/calibrate").queryParam("uid", BOX1.getId()).contentType(MediaType.APPLICATION_JSON))//
                .andExpect(status().isOk())//
                .andReturn()//
                .getResponse()//
                .getContentAsString();
        
        Message message = objectMapper.readValue(mockMvcResult, new TypeReference<>() {});
        
        assertNotNull(message);
        assertThat(message.content()).isEqualTo(String.format("Calibrated box %s, result: %s.", BOX1.getId(), CALIBRATION_RESULT));
    }

	@Test
	void testTare() throws Exception {
	    when(scaleService.tare(eq(BOX1.getId()))).thenReturn(TARE_RESULT);
	    
	    String mockMvcResult = mockMvc.perform(get("/tare").queryParam("uid", BOX1.getId()).contentType(MediaType.APPLICATION_JSON))//
	            .andExpect(status().isOk())//
	            .andReturn()//
	            .getResponse()//
	            .getContentAsString();
	    
	    Message message = objectMapper.readValue(mockMvcResult, new TypeReference<>() {});
	    
	    assertNotNull(message);
	    assertThat(message.content()).isEqualTo(String.format("Tared box %s (%s), result: %s.", BOX1.getId(), BOX1.getDescription(), TARE_RESULT));
	}

}
