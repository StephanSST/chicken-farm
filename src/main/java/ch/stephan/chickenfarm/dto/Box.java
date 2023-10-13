package ch.stephan.chickenfarm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Box {
    public static final Box VORNE = new Box("ZUw", "vorne", BoxState.EMPTY);
    public static final Box HINTEN = new Box("23yp", "hinten", BoxState.EMPTY);

	private String id;
	private String description;
	private BoxState boxState;

}
