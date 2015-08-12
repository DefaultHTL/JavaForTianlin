package org.tianlin.java.exercise6.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
		
		// TODO

		serverHandler = new ServerHandler();
		serverThread = new Thread(serverHandler);
		serverThread.start();
	}

	/*
	 * parsing user info from file
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
	 * Retrieve server thread for main thread to be joined to.
	 */
	public Thread getServerThread() {
		return serverThread;
	}

	public static class ServerHandler implements Runnable {

		@Override
		public void run() {
			Log.i(TAG, "------>> Server Start <<------");
			// TODO Auto-generated method stub
			try {
				Log.i(TAG, "Wait 3s then exit for test...");
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i(TAG, "------>> Server Stop <<------");
		}

	}
}
