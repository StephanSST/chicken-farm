package ch.stephan.chickenfarm.dto;

public record Chicken(String name, int weight) {
	public static final Chicken HEIDI = new Chicken("Heidi", 2200);
	public static final Chicken KLARA = new Chicken("Klara", 2850);
	public static final Chicken LILI = new Chicken("Lili", 2900);
}
