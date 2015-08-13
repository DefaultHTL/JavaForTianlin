package org.tianlin.java.exercise6.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.tianlin.java.exercise6.game.utility.Log;

/*
 * Running on server side to handle a client connection.
 */
public class ClientHandler implements Runnable {
	private static final String TAG = "ClientHandler";
	private static final int BUFFER_LENGTH = 32; // TODO is it too small?

	private byte[] buffer = new byte[BUFFER_LENGTH];
	private GameServer server = null;
	private Socket client = null;

	public ClientHandler(GameServer server, Socket client) {
		this.server = server;
		this.client = client;
	}

	@Override
	public void run() {
		try {
			DataInputStream input = new DataInputStream(client.getInputStream());
			DataOutputStream output = new DataOutputStream(client.getOutputStream());

			/*
			 * authenticate or register
			 */
			int length = 0;
			length = input.read(buffer);
			Log.d(TAG, "Read %d bytes", length);
			String username = new String(buffer, 3, buffer[2]);
			String password = new String(buffer, 4 + buffer[2], buffer[3 + buffer[2]]);
			if (server.authenticate(username, password)) {
				output.writeUTF("OK");
			} else {
				output.writeUTF("Fail");
			}

			output.close();
			input.close();
		} catch (IOException e) {
			Log.e(TAG, "IOException when handling client request: %s\n", e.getMessage());
		} finally {
			Log.i(TAG, "Connection lost");
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					Log.e(TAG, "Error closing client connection!!");
				}
			}
		}
	}

}
