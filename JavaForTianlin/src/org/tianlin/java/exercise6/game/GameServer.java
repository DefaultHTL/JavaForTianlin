package org.tianlin.java.exercise6.game;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.tianlin.java.exercise6.game.utility.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class GameServer {
	private static final String TAG = "GameServer";
	private static final String SERVER_DIR = "server";
	private static final String USER_FILE = "users.xml";
	// private static final String SCHEMA_FILE = "data/UserSchema.xsd";
	public static final String TAG_USERS = "users";
	public static final String TAG_USER = "user";
	public static final String TAG_HERO = "hero";
	public static final String TAG_UNIT = "unit";
	public static final String ATTR_USERNAME = "username";
	public static final String ATTR_PASSWORD = "password";
	public static final String ATTR_HERONAME = "name";
	public static final String ATTR_HEROTYPE = "heroType";
	public static final String ATTR_EXPERIENCE = "exp";
	public static final String ATTR_MONEY = "money";
	public static final String ATTR_UNITNAME = "name";
	public static final String ATTR_UNITTYPE = "unitType";

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
			Log.i(TAG, "Create server directory at %s.", serverDir.getAbsolutePath());
		}

		/*
		 * initialize user info
		 */
		if (!parsingUserInfos(new File(serverDir, USER_FILE))) {
			Log.e(TAG, "Error parsing user info, shutting down server!!");
			return;
		}

		serverHandler = new ServerHandler(this);
		serverThread = new Thread(serverHandler);
		serverThread.start();

		try {
			/*
			 * wait server thread to finish
			 */
			serverThread.join();
		} catch (InterruptedException e) {
			Log.e(TAG, "Game server unexpectedly interrupted.");
		}
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
				writeUserFile(user, true);
				return true;
			} catch (IOException e) {
				Log.e(TAG, "IOException when creating user file: %s", e.getMessage());
				return false;
			}
		}

		try {
			/*
			 * get document
			 */
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.parse(user);
			/*
			 * get root
			 */
			Element root = document.getDocumentElement();
			UserInfo userInfo;

			/*
			 * parse user info
			 */
			NodeList userNodes = root.getElementsByTagName(TAG_USER);
			for (int i = 0; i < userNodes.getLength(); i++) {
				userInfo = UserInfo.parse((Element) userNodes.item(i));
				userInfos.put(userInfo.getUsername(), userInfo);
			}

			Log.i(TAG, "Parsing user file finished, %d users loaded.", userInfos.size());
			return true;
		} catch (Exception e) {
			Log.e(TAG, "Exception when parsing user file: %s", e.getMessage());
			if (user.delete()) {
				Log.w(TAG, "Delete error user file for next running %s", user.getAbsolutePath());
			} else {
				Log.e(TAG, "Cannot delete error user file!!");
			}
		}

		return false;
	}

	/*
	 * record all user info to user file
	 */
	private void writeUserFile(File file, boolean firstCreate) {
		firstCreate = false;
		if (firstCreate) {
			try {
				/*
				 * create document
				 */
				DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document document = builder.newDocument();
				/*
				 * we just create a root node
				 */
				Element root = document.createElement(TAG_USERS);
				document.appendChild(root);
				/*
				 * create transformer
				 */
				Transformer transformer = TransformerFactory.newInstance().newTransformer();
				transformer.setOutputProperty("indent", "yes");
				/*
				 * create source and result
				 */
				DOMSource source = new DOMSource(document);
				StreamResult result = new StreamResult(file);
				/*
				 * use transformer to transform source to result
				 */
				transformer.transform(source, result);
			} catch (Exception e) {
				Log.e(TAG, "Exception when writing user file: %s", e.getMessage());
			}
			Log.i(TAG, "Writing user file finished, it's the first time writing.");
		} else {
			// TODO write all users
		}
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
