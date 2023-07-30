package ch.stephan.chickenfarm.dto;

public record Discovery(String uid, String parentUid, String position, String hardwareVersion, String firmwareVersion,
		String deviceIdentifier, String enumerationType) {
}
