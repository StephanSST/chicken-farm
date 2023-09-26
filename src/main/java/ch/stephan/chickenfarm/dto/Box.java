package ch.stephan.chickenfarm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Box {

	private String id;
	private String description;
	private BoxState boxState;

}
