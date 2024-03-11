package ch.stephan.chickenfarm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Box {
	public static final Box VORNE = new Box("2", "vorne", BoxState.EMPTY, 0);
	public static final Box HINTEN = new Box("1", "hinten", BoxState.EMPTY, 0);

	private String id;
	private String description;
	private BoxState boxState;
	private int weight;

}
