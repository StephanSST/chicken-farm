package ch.stephan.chickenfarm.dto;

public record Discovery(String uid, String connectedUid, int position, String hardwareVersion, String firmwareVersion,
		int deviceIdentifier, int enumerationType) {
}
