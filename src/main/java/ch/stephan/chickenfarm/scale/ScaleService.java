package ch.stephan.chickenfarm.scale;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tinkerforge.AlreadyConnectedException;
import com.tinkerforge.BrickletLoadCellV2;
import com.tinkerforge.IPConnection;
import com.tinkerforge.NetworkException;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TinkerforgeException;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Service
public class ScaleService {

	private static final String HOST = "localhost";
	private static final int PORT = 4223;
	private static final String UID = "ZUw";

	private IPConnection ipConnection;
	private Map<String, List<String>> discoveryResult = new HashMap<>();

	public int measureWeight(int box) {
		int weight = -1;
		try {
			BrickletLoadCellV2 loadCell = new BrickletLoadCellV2(UID, ipConnection);
			weight = loadCell.getWeight();
			System.out.println("Weight: " + weight + " g");

		} catch (TinkerforgeException ex) {
			ex.printStackTrace();
		}

		return weight;
	}

	public Map<String, List<String>> discovery() {
		try {
			ipConnection.enumerate();
			System.out.println("Broadcast sent to all connected components");

			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				// just ignore it
			}

		} catch (TinkerforgeException ex) {
			ex.printStackTrace();
		}
		return discoveryResult;
	}

	@PostConstruct
	public void initIPConnection() {
		try {
			ipConnection = new IPConnection();
			ipConnection.connect(HOST, PORT);
			ipConnection.addEnumerateListener(new EnumerateListenerImpl());
		} catch (AlreadyConnectedException ex) {
			ex.printStackTrace();
		} catch (NetworkException ex) {
			ex.printStackTrace();
		}
	}

	@PreDestroy
	public void shutdownIPConnection() {
		try {
			ipConnection.disconnect();
			ipConnection.close();
		} catch (NotConnectedException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private final class EnumerateListenerImpl implements IPConnection.EnumerateListener {
		@Override
		public void enumerate(String uid, String connectedUid, char position, short[] hardwareVersion,
				short[] firmwareVersion, int deviceIdentifier, short enumerationType) {

			List<String> result = new ArrayList<String>();
			result.add("UID:               " + uid);
			result.add("Enumeration Type:  " + enumerationType);

			if (enumerationType != IPConnection.ENUMERATION_TYPE_DISCONNECTED) {
				result.add("Connected UID:     " + connectedUid);
				result.add("Position:          " + position);
				result.add("Hardware Version:  " + asString(hardwareVersion));
				result.add("Firmware Version:  " + asString(firmwareVersion));
				result.add("Device Identifier: " + deviceIdentifier);
			}
			result.add("");

			discoveryResult.put(uid, result);
		}

		private String asString(short[] version) {
			return version[0] + "." + version[1] + "." + version[2];
		}

	}

}
