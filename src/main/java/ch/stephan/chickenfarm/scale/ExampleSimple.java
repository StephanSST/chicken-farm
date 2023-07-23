package ch.stephan.chickenfarm.scale;

import java.io.IOException;

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

			weight = loadCell.getWeight();
			System.out.println("Weight: " + weight + " g");

			ipConn.disconnect();
			ipConn.close();

		} catch (AlreadyConnectedException ex) {
			ex.printStackTrace();
		} catch (NetworkException ex) {
			ex.printStackTrace();
		} catch (TinkerforgeException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return weight;
	}

	public static void discovery() {
		try {
			IPConnection ipConn = new IPConnection();
			ipConn.connect(HOST, PORT);

			// Register enumerate listener and print incoming information
			ipConn.addEnumerateListener(new IPConnection.EnumerateListener() {
				@Override
				public void enumerate(String uid, String connectedUid, char position, short[] hardwareVersion,
						short[] firmwareVersion, int deviceIdentifier, short enumerationType) {
					System.out.println("UID:               " + uid);
					System.out.println("Enumeration Type:  " + enumerationType);

					if (enumerationType == IPConnection.ENUMERATION_TYPE_DISCONNECTED) {
						System.out.println("");
						return;
					}

					System.out.println("Connected UID:     " + connectedUid);
					System.out.println("Position:          " + position);
					System.out.println("Hardware Version:  " + hardwareVersion[0] + "." + hardwareVersion[1] + "."
							+ hardwareVersion[2]);
					System.out.println("Firmware Version:  " + firmwareVersion[0] + "." + firmwareVersion[1] + "."
							+ firmwareVersion[2]);
					System.out.println("Device Identifier: " + deviceIdentifier);
					System.out.println("");
				}
			});

			ipConn.enumerate();

			ipConn.disconnect();
			ipConn.close();

		} catch (AlreadyConnectedException ex) {
			ex.printStackTrace();
		} catch (NetworkException ex) {
			ex.printStackTrace();
		} catch (TinkerforgeException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}