package org.tianlin.java.exercise6.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.tianlin.java.exercise6.game.utility.Log;

public class GameClient {
	private static final String TAG = "GameClient";
	private static final String SERVER_ADDR = "localhost";
	private static final int SERVER_PORT = 30001;

	private static final int BUFFER_LENGTH = 32;
	private byte[] buffer = new byte[BUFFER_LENGTH];

	public void run() {
		Log.i(TAG, "------>> Client Start <<------");
		Socket server = null;
		DataInputStream input = null;
		DataOutputStream output = null;
		Scanner scanner = null;

		try {
			server = new Socket(SERVER_ADDR, SERVER_PORT);
			Log.i(TAG, "Connection established! Server address: %s\n", server.getInetAddress().getHostAddress());

			input = new DataInputStream(server.getInputStream());
			output = new DataOutputStream(server.getOutputStream());
			scanner = new Scanner(System.in);
			int choose = 0;

			/*
			 * sign in or sign up
			 */
			p("Welcome to Hero Fight!!!\n\n");
			p("    1. Sign in\n");
			p("    2. Sign up\n");
			p("    3. Exit\n");
			choose = getUserInputInRange(1, 3, scanner);

			if (choose == 3) {
				p("Bye-bye!\n");
				Log.i(TAG, "Exiting...");
				return;
			} else if (choose == 2) {
				// TODO
			} else {
				p("Username: ");
				String username = scanner.next();
				p("Password: ");
				String password = scanner.next();
				byte[] ub = username.getBytes();
				byte[] pb = password.getBytes();
				buffer[0] = 0x01;
				buffer[1] = (byte) (2 + ub.length + pb.length);
				buffer[2] = (byte) ub.length;
				System.arraycopy(ub, 0, buffer, 3, ub.length);
				buffer[ub.length + 3] = (byte) pb.length;
				System.arraycopy(pb, 0, buffer, ub.length + 4, pb.length);
				output.write(buffer, 0, buffer[1] + 2);

				String response = input.readUTF();
				p("Authentication result: %s\n", response);
			}
		} catch (UnknownHostException e) {
			Log.e(TAG, "Connection failed, unknown host: %s", SERVER_ADDR);
		} catch (IOException e) {
			Log.e(TAG, "IOException: %s\n", e.getMessage());
		} finally {
			try {
				if (scanner != null)
					scanner.close();
				if (output != null)
					output.close();
				if (input != null)
					input.close();
				if (server != null)
					server.close();
			} catch (IOException e) {
				Log.e(TAG, "IOException when closing socket: %s\n", e.getMessage());
			}
		}
		Log.i(TAG, "------>> Client Stop <<------");
	}

	/*
	 * Get what we want from user input.
	 */
	private int getUserInputInRange(int min, int max, Scanner scanner) {
		int n = max + 1;
		do {
			try {
				n = scanner.nextInt();
			} catch (InputMismatchException e) {
				n = max + 1;
			}
			if (n >= min && n <= max) {
				break;
			} else {
				p("Wrong input, please choose from %d to %d.", min, max);
			}
		} while (true);

		return n;
	}

	/*
	 * convenient for System.out.printf()
	 */
	private void p(String pattern, Object... args) {
		System.out.printf(pattern, args);
	}
}
