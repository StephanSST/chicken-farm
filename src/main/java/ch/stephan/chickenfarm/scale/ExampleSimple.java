package ch.stephan.chickenfarm.scale;

import com.tinkerforge.AlreadyConnectedException;
import com.tinkerforge.BrickletLoadCellV2;
import com.tinkerforge.IPConnection;
import com.tinkerforge.NetworkException;
import com.tinkerforge.TinkerforgeException;

public class ExampleSimple {
	private static final String HOST = "localhost";
	private static final int PORT = 4223;
	private static final String UID = "ZUw";

	public static int measureWeight() {
		int weight = -1;
		try {
			IPConnection ipConn = new IPConnection();
			BrickletLoadCellV2 loadCell = new BrickletLoadCellV2(UID, ipConn);

			ipConn.connect(HOST, PORT);
			// Don't use device before ipcon is connected

			weight = loadCell.getWeight();
			System.out.println("Weight: " + weight + " g");

			ipConn.disconnect();

		} catch (AlreadyConnectedException ex) {
			ex.printStackTrace();
		} catch (NetworkException ex) {
			ex.printStackTrace();
		} catch (TinkerforgeException ex) {
			ex.printStackTrace();
		}

		return weight;
	}

}