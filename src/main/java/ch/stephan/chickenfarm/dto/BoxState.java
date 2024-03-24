package ch.stephan.chickenfarm.dto;

import java.util.Arrays;

public enum BoxState {
	EMPTY, CHICKEN_IN, EGG_IN;

	public static BoxState findByName(String name) {
		return Arrays.asList(values()).stream()//
				.filter(bs -> bs.name().equalsIgnoreCase(name))//
				.findFirst()//
				.orElse(null);
	}
}
