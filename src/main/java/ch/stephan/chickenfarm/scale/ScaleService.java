package ch.stephan.chickenfarm.scale;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tinkerforge.AlreadyConnectedException;
import com.tinkerforge.BrickHAT;
import com.tinkerforge.BrickletLoadCellV2;
import com.tinkerforge.IPConnection;
import com.tinkerforge.NetworkException;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TinkerforgeException;

import ch.stephan.chickenfarm.dto.Discovery;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Service
public class ScaleService {

	private static final String HOST = "localhost";
	private static final int PORT = 4223;
	private static final String UID = "ZUw";

	private IPConnection ipConnection;
	private List<Discovery> discoveryResult = new ArrayList<>();

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

	public String calibrate(int box) {
		try {
			BrickletLoadCellV2 loadCell = new BrickletLoadCellV2(UID, ipConnection);
			int before = loadCell.getWeight();
			loadCell.calibrate(before);
			int after = loadCell.getWeight();
			System.out.println("Scale was recalibrated. Before: " + before + " g - after: " + after + " g");

		} catch (TinkerforgeException ex) {
			ex.printStackTrace();
		}

		return "successfully calibrated";
	}

	public List<Discovery> discovery() {
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

			Discovery discovery = new Discovery(uid, connectedUid, Character.toString(position),
					asString(hardwareVersion), asString(firmwareVersion), deviceAsString(deviceIdentifier),
					enumAsString(enumerationType));
			discoveryResult.add(discovery);
		}

		private String asString(short[] version) {
			return version[0] + "." + version[1] + "." + version[2];
		}

		private String enumAsString(short enumerationType) {
			switch (enumerationType) {
			case IPConnection.ENUMERATION_TYPE_AVAILABLE:
				return "AVAILABLE";
			case IPConnection.ENUMERATION_TYPE_CONNECTED:
				return "CONNECTED";
			case IPConnection.ENUMERATION_TYPE_DISCONNECTED:
				return "DISCONNECTED";
			default:
				return "UNKNOWN";
			}
		}

		private String deviceAsString(int deviceIdentifier) {
			switch (deviceIdentifier) {
			case BrickletLoadCellV2.DEVICE_IDENTIFIER:
				return "Load Cell V2 Bricklet";
			case BrickHAT.DEVICE_IDENTIFIER:
				return "HAT Brick";
			default:
				return String.valueOf(deviceIdentifier);
			}
		}

	}

}
