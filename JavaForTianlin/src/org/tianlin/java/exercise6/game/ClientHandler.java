package org.tianlin.java.exercise6.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.tianlin.java.exercise6.game.utility.CoDec;
import org.tianlin.java.exercise6.game.utility.Log;
import org.tianlin.java.exercise6.game.utility.CoDec.DecodeResult;
import org.tianlin.java.exercise6.game.utility.CommandEnum;

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

			boolean exit = false;
			int length = 0;
			DecodeResult result = null;
			while (!exit) {
				length = input.read(buffer);
				/*
				 * workaround for infinite receiving EOF when client closed
				 */
				if (length == -1) {
					Log.w(TAG, "EOF reached, is client closed?");
					break;
				}
				Log.d(TAG, "Receive %d bytes.", length);
				result = CoDec.decode(buffer, length);
				if (result.valid) {
					exit = handleCommands(result, input, output);
				}
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

	/*
	 * we don't check arguments here because they were already guaranteed in
	 * decoder
	 */
	private boolean handleCommands(DecodeResult result, DataInputStream input, DataOutputStream output)
			throws IOException {
		boolean exit = false;
		byte[] sendBack = null;
		switch (result.command) {
		case SignIn:
			boolean authResult = server.authenticate(result.arguments[0].toString(), result.arguments[1].toString());
			sendBack = CoDec.encode(CommandEnum.SignInResult, authResult);
			output.write(sendBack);
			if (authResult) {
				// TODO succeed, do other things: record session and so on
			}
			break;
		case ClientExit:
			Log.i(TAG, "Client says bye-bye, shutting down...");
			exit = true;
			break;
		default:
			Log.w(TAG, "Ignore unexpected command: %s", result.command.name());
		}
		return exit;
	}
}
