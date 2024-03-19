package ch.stephan.chickenfarm.mqtt;

import java.util.StringTokenizer;

import ch.stephan.chickenfarm.registry.BoxService;

public final class ChickenFarmMessageHandler {

	public static void handleWeightMessage(String payload, BoxService boxService) {
		StringTokenizer tokenizer = new StringTokenizer(payload, ";");
		while (tokenizer.hasMoreTokens()) {
			String[] keyValue = tokenizer.nextToken().split(":");
			String boxId = keyValue[0].substring(1);
			int weight = Float.valueOf(keyValue[1]).intValue();
			boxService.getBox(boxId).setWeight(weight);
		}
	}

}
