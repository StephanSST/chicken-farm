package ch.stephan.chickenfarm.dto;

public record Discovery(String deviceIdentifier, String uid, String parentUid, String position, String enumerationType,
		String hardwareVersion, String firmwareVersion) {
}
