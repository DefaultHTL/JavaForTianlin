package org.tianlin.java.exercise6.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.tianlin.java.exercise6.game.utility.Log;

public class GameServer {
	private static final String TAG = "GameServer";
	private static final String SERVER_DIR = "server";
	private static final String USER_FILE = "user.txt";

	public static final int PORT = 30001;

	private Thread serverThread = null;
	private ServerHandler serverHandler = null;

	private Map<String, UserInfo> userInfos = new HashMap<String, UserInfo>();

	/*
	 * Everything begins!
	 */
	public void run() {
		/*
		 * check server directory
		 */
		File serverDir = new File(SERVER_DIR);
		if (!serverDir.exists()) {
			Log.w(TAG, "Missing server directory, it's the first time running.");
			if (!serverDir.mkdirs()) {
				Log.e(TAG, "Cannot create server directory at %s, shutting down server!!", serverDir.getAbsolutePath());
				return;
			}
			Log.w(TAG, "Create server directory at %s.", serverDir.getAbsolutePath());
		}

		/*
		 * initialize user info
		 */
		if (!parsingUserInfos(new File(serverDir, USER_FILE))) {
			Log.e(TAG, "Error parsing user info, shutting down server!!");
			return;
		}

		// TODO server global initializations

		serverHandler = new ServerHandler(this);
		serverThread = new Thread(serverHandler);
		serverThread.start();
	}

	/*
	 * parsing user info from file TODO change to xml file
	 */
	private boolean parsingUserInfos(File user) {
		if (!user.exists()) {
			Log.w(TAG, "Missing user file, create it at %s", user.getAbsolutePath());
			try {
				if (!user.createNewFile()) {
					Log.e(TAG, "Cannot create user file!");
					return false;
				}
				return true;
			} catch (IOException e) {
				Log.e(TAG, "IOException when creating user file: %s", e.getMessage());
				return false;
			}
		}

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(user)));
			String line = null;
			UserInfo userInfo = null;
			while ((line = reader.readLine()) != null) {
				userInfo = UserInfo.parse(line);
				if (userInfo != null)
					userInfos.put(userInfo.getUsername(), userInfo);
				else
					Log.w(TAG, "Error parsing userInfo, ignored. Line: %s", line);
			}
			Log.i(TAG, "%d users parsed.", userInfos.size());
		} catch (IOException e) {
			Log.e(TAG, "IOException when reading user file: %s", e.getMessage());
			return false;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					Log.e(TAG, "IOException when closing user file: %s", e.getMessage());
					return false;
				}
			}
		}

		return true;
	}

	/*
	 * Authenticate username and password for handlers
	 */
	public boolean authenticate(String username, String password) {
		UserInfo info = userInfos.get(username);
		if (info != null && info.getPassword().equals(password)) {
			Log.i(TAG, "Authenticate succeed, username: %s and password: %s.", username, password);
			return true;
		}

		Log.i(TAG, "Authenticate failed, username: %s and password: %s.", username, password);
		return false;
	}

	/*
	 * Retrieve server thread for main thread to be joined to.
	 */
	public Thread getServerThread() {
		return serverThread;
	}

	/*
	 * Running on server side listening connections.
	 */
	public static class ServerHandler implements Runnable {
		private GameServer server = null;

		public ServerHandler(GameServer server) {
			this.server = server;
		}

		@Override
		public void run() {
			/*
			 * create server socket
			 */
			ServerSocket serverSocket = null;
			try {
				Log.i(TAG, "------>> Server Start <<------");
				serverSocket = new ServerSocket(PORT);
				Log.i(TAG, "Start listening on port %d", serverSocket.getLocalPort());

				while (true) {
					Socket clientSocket = serverSocket.accept();
					Log.i(TAG, "Connection established from %s", clientSocket.getInetAddress().getHostAddress());
					new Thread(new ClientHandler(server, clientSocket)).start();
				}

			} catch (IOException e) {
				Log.e(TAG, "Error opening server socket on port %d, Exception: %s", PORT, e.getMessage());
			} finally {
				if (serverSocket != null) {
					try {
						serverSocket.close();
					} catch (IOException e) {
						Log.e(TAG, "IOException when closing server: %s", e.getMessage());
					}
				}
				Log.i(TAG, "------>> Server Stop <<------");
			}
		}
	}
}
