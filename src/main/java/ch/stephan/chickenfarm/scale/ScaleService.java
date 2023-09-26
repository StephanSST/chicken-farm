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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ScaleService {

	private static final String HOST = "localhost";
	private static final int PORT = 4223;

	private IPConnection ipConnection;
	private List<Discovery> discoveryResult;
	EnumerateListenerImpl enumerateListener = new EnumerateListenerImpl();

	public ScaleService() {
		super();
	}

	public ScaleService(IPConnection ipConnection) {
		super();
		this.ipConnection = ipConnection;
	}

	public int measureWeight(String uid) {
		int weight = -1;
		try {
			BrickletLoadCellV2 loadCell = new BrickletLoadCellV2(uid, ipConnection);
			weight = loadCell.getWeight();
			log.info("Scale {} has weight {}g.", uid, weight);

		} catch (TinkerforgeException ex) {
			ex.printStackTrace();
		}

		return weight;
	}

	public String calibrate(String uid) {
		try {
			BrickletLoadCellV2 loadCell = new BrickletLoadCellV2(uid, ipConnection);
			int before = loadCell.getWeight();
			loadCell.calibrate(before);
			int after = loadCell.getWeight();
			log.info("Scale {} was recalibrated. Before: {}g - after: {}g.", uid, before, after);

		} catch (TinkerforgeException ex) {
			ex.printStackTrace();
		}

		return "successfully calibrated";
	}

	public String tare(String uid) {
		try {
			BrickletLoadCellV2 loadCell = new BrickletLoadCellV2(uid, ipConnection);
			loadCell.tare();
			log.info("Scale {} has been tared.", uid);

		} catch (TinkerforgeException ex) {
			ex.printStackTrace();
		}

		return "successfully tared";
	}

	public List<Discovery> discovery() {
		try {
			discoveryResult = new ArrayList<>();

			ipConnection.enumerate();
			log.info("Broadcast sent to all connected components");

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
			ipConnection.removeEnumerateListener(enumerateListener);
			ipConnection.addEnumerateListener(enumerateListener);
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

			Discovery discovery = new Discovery(deviceAsString(deviceIdentifier), uid, connectedUid,
					Character.toString(position), enumAsString(enumerationType), asString(hardwareVersion),
					asString(firmwareVersion));
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
