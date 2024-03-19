package ch.stephan.chickenfarm.dto;

public enum Chicken {
	HEIDI("Heidi", 2200), //
	KLARA("Klara", 2850), //
	LILI("Lili", 2851), //
	LULU("Lulu", 3450);

	private String name;
	private int weight;

	private Chicken(String name, int weight) {
		this.name = name;
		this.weight = weight;
	}

	public String getDisplayName() {
		return name;
	}

	public int getWeight() {
		return weight;
	}

}
