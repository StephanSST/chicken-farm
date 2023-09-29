package ch.stephan.chickenfarm.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.stephan.chickenfarm.dto.Message;
import ch.stephan.chickenfarm.messenger.MessengerService;
import ch.stephan.chickenfarm.scale.ScaleService;

@WebMvcTest(value = MeasureController.class)
class MeasureControllerTest {

	private static final String MESSAGE_TEXT = "Huhn hat ein Ei gelegt";
	private static final String CALIBRATION_RESULT = "successfully calibrated";
	private static final String UID1 = "23yp";
	private static final String UID2 = "ZUw";
	private static final int CURRENT_WEIGHT1 = 666;
	private static final int CURRENT_WEIGHT2 = 667;

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
        when(scaleService.measureWeight(eq(UID1))).thenReturn(CURRENT_WEIGHT1);
        when(scaleService.measureWeight(eq(UID2))).thenReturn(CURRENT_WEIGHT2);

        String mockMvcResult = mockMvc.perform(get("/measure").contentType(MediaType.APPLICATION_JSON))//
                .andExpect(status().isOk())//
                .andReturn()//
                .getResponse()//
                .getContentAsString();

        Message message = objectMapper.readValue(mockMvcResult, new TypeReference<>() {});

        assertNotNull(message);
        assertThat(message.content()).isEqualTo("Weight of box 23yp (hinten) is 666, Weight of box ZUw (vorne) is 667.");
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
